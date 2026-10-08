package com.bookworm.controller;

import com.bookworm.dto.DTOs.*;
import com.bookworm.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
@Tag(name = "Orders & Checkout", description = "Endpoints for Checkout, Order History, Buy Again, and 48hr Cancellation")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping("/orders/checkout")
    @Operation(summary = "Checkout current shopping cart and create order")
    public ResponseEntity<OrderResponse> checkout(@RequestBody CheckoutRequest req) {
        return new ResponseEntity<>(orderService.checkout(req), HttpStatus.CREATED);
    }

    @GetMapping("/orders/user/{userId}")
    @Operation(summary = "Get user order history")
    public ResponseEntity<List<OrderResponse>> getUserOrders(@PathVariable Long userId) {
        return ResponseEntity.ok(orderService.getUserOrders(userId));
    }

    @GetMapping("/orders/{orderId}")
    @Operation(summary = "Get single order details")
    public ResponseEntity<OrderResponse> getOrderById(@PathVariable Long orderId) {
        return ResponseEntity.ok(orderService.getOrderById(orderId));
    }

    @PostMapping("/orders/{orderId}/cancel")
    @Operation(summary = "Cancel order (Within 48 hours)")
    public ResponseEntity<OrderResponse> cancelOrder(@PathVariable Long orderId, @RequestBody(required = false) CancelOrderRequest req) {
        String reason = req != null ? req.getReason() : "Customer requested cancellation";
        return ResponseEntity.ok(orderService.cancelOrder(orderId, reason));
    }

    @PostMapping("/orders/{orderId}/buy-again")
    @Operation(summary = "Re-add past order items to cart (Buy In Again)")
    public ResponseEntity<CartDto> buyAgain(@PathVariable Long orderId, @RequestParam Long userId) {
        return ResponseEntity.ok(orderService.buyAgain(orderId, userId));
    }

    @PostMapping("/payments/redeem-points")
    @Operation(summary = "Redeem gift/reward points for discounts")
    public ResponseEntity<RedeemPointsResponse> redeemPoints(@RequestBody RedeemPointsRequest req) {
        return ResponseEntity.ok(orderService.redeemPoints(req));
    }
}
