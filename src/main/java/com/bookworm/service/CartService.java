package com.bookworm.service;

import com.bookworm.dto.DTOs.*;
import com.bookworm.model.Book;
import com.bookworm.model.Cart;
import com.bookworm.model.CartItem;
import com.bookworm.model.User;
import com.bookworm.repository.BookRepository;
import com.bookworm.repository.CartItemRepository;
import com.bookworm.repository.CartRepository;
import com.bookworm.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository,
                       CartItemRepository cartItemRepository,
                       BookRepository bookRepository,
                       UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public Cart getOrCreateCart(Long userId) {
        return cartRepository.findByUserId(userId).orElseGet(() -> {
            User user = userRepository.findById(userId)
                    .orElseThrow(() -> new RuntimeException("User not found: " + userId));
            Cart cart = new Cart(user);
            return cartRepository.save(cart);
        });
    }

    public CartDto getCartDto(Long userId) {
        Cart cart = getOrCreateCart(userId);
        return mapToCartDto(cart);
    }

    public CartDto addToCart(AddToCartRequest req) {
        Cart cart = getOrCreateCart(req.getUserId());
        Book book = bookRepository.findById(req.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found: " + req.getBookId()));

        CartItem item = cartItemRepository.findByCartIdAndBookId(cart.getId(), book.getId())
                .orElse(null);

        if (item != null) {
            item.setQuantity(item.getQuantity() + (req.getQuantity() != null ? req.getQuantity() : 1));
            cartItemRepository.save(item);
        } else {
            item = new CartItem(cart, book, req.getQuantity() != null ? req.getQuantity() : 1);
            cartItemRepository.save(item);
            cart.getItems().add(item);
        }

        return mapToCartDto(cartRepository.save(cart));
    }

    public CartDto updateCartItem(Long cartItemId, Integer quantity) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found: " + cartItemId));

        if (quantity <= 0) {
            Cart cart = item.getCart();
            cart.getItems().remove(item);
            cartItemRepository.delete(item);
            return mapToCartDto(cartRepository.save(cart));
        }

        item.setQuantity(quantity);
        cartItemRepository.save(item);
        return mapToCartDto(item.getCart());
    }

    public CartDto removeCartItem(Long cartItemId) {
        CartItem item = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new RuntimeException("Cart item not found: " + cartItemId));
        Cart cart = item.getCart();
        cart.getItems().remove(item);
        cartItemRepository.delete(item);
        return mapToCartDto(cartRepository.save(cart));
    }

    public void clearCart(Long userId) {
        Cart cart = getOrCreateCart(userId);
        cart.getItems().clear();
        cartRepository.save(cart);
    }

    public CartDto mapToCartDto(Cart cart) {
        CartDto dto = new CartDto();
        dto.setCartId(cart.getId());
        dto.setUserId(cart.getUser() != null ? cart.getUser().getId() : null);

        List<CartItemDto> itemDtos = new ArrayList<>();
        BigDecimal totalPrice = BigDecimal.ZERO;
        int totalQuantity = 0;

        if (cart.getItems() != null) {
            for (CartItem item : cart.getItems()) {
                CartItemDto itemDto = new CartItemDto();
                itemDto.setId(item.getId());
                itemDto.setBookId(item.getBook().getId());
                itemDto.setTitle(item.getBook().getTitle());
                itemDto.setAuthor(item.getBook().getAuthor());
                itemDto.setFormat(item.getBook().getFormat());
                itemDto.setPrice(item.getBook().getPrice());
                itemDto.setQuantity(item.getQuantity());
                itemDto.setSubTotal(item.getSubTotal());
                itemDto.setImageUrl(item.getBook().getImageUrl());
                itemDto.setDeliveryDate(item.getBook().getTentativeDeliveryDate());
                itemDtos.add(itemDto);

                totalPrice = totalPrice.add(item.getSubTotal());
                totalQuantity += item.getQuantity();
            }
        }

        dto.setItems(itemDtos);
        dto.setItemCount(totalQuantity);
        dto.setTotalPrice(totalPrice);

        // Taxes ~12%
        BigDecimal tax = totalPrice.multiply(new BigDecimal("0.12")).setScale(2, RoundingMode.HALF_UP);
        dto.setTax(tax);

        // Free delivery above 300, else 50
        BigDecimal delivery = (totalPrice.compareTo(new BigDecimal("300.00")) >= 0 || totalPrice.compareTo(BigDecimal.ZERO) == 0)
                ? BigDecimal.ZERO : new BigDecimal("50.00");
        dto.setDeliveryCharges(delivery);

        // Default discount placeholder
        dto.setDiscount(BigDecimal.ZERO);
        dto.setGrandTotal(totalPrice.add(tax).add(delivery));

        return dto;
    }
}
