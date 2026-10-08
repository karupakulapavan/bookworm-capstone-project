package com.bookworm.service;

import com.bookworm.dto.DTOs.*;
import com.bookworm.model.*;
import com.bookworm.repository.AddressRepository;
import com.bookworm.repository.OrderRepository;
import com.bookworm.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final UserRepository userRepository;
    private final AddressRepository addressRepository;
    private final PaymentAndShippingService paymentAndShippingService;
    private final MemberService memberService;

    public OrderService(OrderRepository orderRepository,
                        CartService cartService,
                        UserRepository userRepository,
                        AddressRepository addressRepository,
                        PaymentAndShippingService paymentAndShippingService,
                        MemberService memberService) {
        this.orderRepository = orderRepository;
        this.cartService = cartService;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
        this.paymentAndShippingService = paymentAndShippingService;
        this.memberService = memberService;
    }

    public OrderResponse checkout(CheckoutRequest req) {
        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found: " + req.getUserId()));

        Address address = addressRepository.findById(req.getAddressId())
                .orElseThrow(() -> new RuntimeException("Address not found: " + req.getAddressId()));

        Cart cart = cartService.getOrCreateCart(req.getUserId());
        if (cart.getItems() == null || cart.getItems().isEmpty()) {
            throw new RuntimeException("Cannot checkout with an empty cart");
        }

        CartDto cartDto = cartService.getCartDto(req.getUserId());

        BigDecimal itemsPrice = cartDto.getTotalPrice();
        BigDecimal tax = itemsPrice.multiply(new BigDecimal("0.12")).setScale(2, RoundingMode.HALF_UP);
        BigDecimal deliveryCharges = itemsPrice.compareTo(new BigDecimal("300.00")) >= 0 ? BigDecimal.ZERO : new BigDecimal("50.00");
        BigDecimal discount = BigDecimal.ZERO;

        // Apply Coupon
        if (req.getCouponCode() != null && !req.getCouponCode().isBlank()) {
            CouponResponse couponRes = paymentAndShippingService.validateCoupon(
                    new CouponRequest() {{ setCouponCode(req.getCouponCode()); setCartTotal(itemsPrice); }}
            );
            if (couponRes.getIsValid()) {
                discount = discount.add(couponRes.getDiscountAmount());
            }
        }

        // Redeem Gift Points
        if (req.getPointsRedeemed() != null && req.getPointsRedeemed() > 0) {
            int availablePoints = user.getGiftPoints() != null ? user.getGiftPoints() : 0;
            int pointsToUse = Math.min(availablePoints, req.getPointsRedeemed());
            discount = discount.add(BigDecimal.valueOf(pointsToUse));
            user.setGiftPoints(availablePoints - pointsToUse);
            userRepository.save(user);
        }

        BigDecimal totalAmount = itemsPrice.add(tax).add(deliveryCharges).subtract(discount);
        if (totalAmount.compareTo(BigDecimal.ZERO) < 0) {
            totalAmount = BigDecimal.ZERO;
        }

        // Create Order
        Order order = new Order();
        order.setOrderNumber("ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        order.setUser(user);
        order.setShippingAddress(address);
        order.setStatus("CONFIRMED");
        order.setCreatedAt(LocalDateTime.now());
        order.setCancellableUntil(LocalDateTime.now().plusHours(48));
        order.setTotalItems(cartDto.getItemCount());
        order.setItemsPrice(itemsPrice);
        order.setTax(tax);
        order.setDeliveryCharges(deliveryCharges);
        order.setDiscount(discount);
        order.setTotalAmount(totalAmount);
        order.setTentativeDeliveryDate(LocalDateTime.now().plusDays(3).format(DateTimeFormatter.ofPattern("EEE, dd MMM")));
        order.setPaymentMethod(req.getPaymentMethod() != null ? req.getPaymentMethod() : "CREDIT_CARD");
        order.setPaymentStatus("PAID");
        order.setTransactionId("TXN-" + UUID.randomUUID().toString().substring(0, 10).toUpperCase());

        List<OrderItem> orderItems = new ArrayList<>();
        for (CartItem ci : cart.getItems()) {
            OrderItem oi = new OrderItem(order, ci.getBook(), ci.getQuantity(), ci.getBook().getPrice());
            orderItems.add(oi);

            // Update copies sold
            Book book = ci.getBook();
            book.setCopiesSold((book.getCopiesSold() != null ? book.getCopiesSold() : 0) + ci.getQuantity());
        }
        order.setItems(orderItems);

        Order savedOrder = orderRepository.save(order);

        // Clear user cart
        cartService.clearCart(user.getId());

        return mapToOrderResponse(savedOrder);
    }

    public List<OrderResponse> getUserOrders(Long userId) {
        return orderRepository.findByUserIdOrderByCreatedAtDesc(userId).stream()
                .map(this::mapToOrderResponse)
                .collect(Collectors.toList());
    }

    public OrderResponse getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));
        return mapToOrderResponse(order);
    }

    public OrderResponse cancelOrder(Long orderId, String reason) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));

        if (!order.canCancelOrder()) {
            throw new RuntimeException("Order cancellation period (48 hours) has expired or order is already processed.");
        }

        order.setStatus("CANCELLED");
        order.setPaymentStatus("REFUNDED");
        Order updated = orderRepository.save(order);
        return mapToOrderResponse(updated);
    }

    public CartDto buyAgain(Long orderId, Long userId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found: " + orderId));

        for (OrderItem item : order.getItems()) {
            cartService.addToCart(new AddToCartRequest() {{
                setUserId(userId);
                setBookId(item.getBook().getId());
                setQuantity(item.getQuantity());
            }});
        }

        return cartService.getCartDto(userId);
    }

    public RedeemPointsResponse redeemPoints(RedeemPointsRequest req) {
        User user = userRepository.findById(req.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found: " + req.getUserId()));

        int available = user.getGiftPoints() != null ? user.getGiftPoints() : 0;
        int redeem = Math.min(available, req.getPointsToRedeem());

        return new RedeemPointsResponse(
                redeem,
                BigDecimal.valueOf(redeem),
                available - redeem,
                "APPLIED"
        );
    }

    private OrderResponse mapToOrderResponse(Order order) {
        OrderResponse res = new OrderResponse();
        res.setOrderId(order.getId());
        res.setOrderNumber(order.getOrderNumber());
        res.setUserId(order.getUser() != null ? order.getUser().getId() : null);
        res.setStatus(order.getStatus());
        res.setCreatedAt(order.getCreatedAt());
        res.setTotalItems(order.getTotalItems());
        res.setItemsPrice(order.getItemsPrice());
        res.setTax(order.getTax());
        res.setDeliveryCharges(order.getDeliveryCharges());
        res.setDiscount(order.getDiscount());
        res.setTotalAmount(order.getTotalAmount());
        res.setTentativeDeliveryDate(order.getTentativeDeliveryDate());
        res.setPaymentStatus(order.getPaymentStatus());
        res.setCancellableUntil(order.getCancellableUntil());
        res.setCanCancel(order.canCancelOrder());

        if (order.getShippingAddress() != null) {
            res.setShippingAddress(memberService.mapToAddressDto(order.getShippingAddress()));
        }

        List<OrderItemDto> items = order.getItems().stream().map(oi -> {
            OrderItemDto dto = new OrderItemDto();
            dto.setBookId(oi.getBook().getId());
            dto.setTitle(oi.getBook().getTitle());
            dto.setAuthor(oi.getBook().getAuthor());
            dto.setPrice(oi.getPrice());
            dto.setQuantity(oi.getQuantity());
            dto.setFormat(oi.getBook().getFormat());
            dto.setImageUrl(oi.getBook().getImageUrl());
            return dto;
        }).collect(Collectors.toList());

        res.setItems(items);
        return res;
    }
}
