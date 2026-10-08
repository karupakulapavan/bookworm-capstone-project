package com.bookworm.controller;

import com.bookworm.dto.DTOs.*;
import com.bookworm.service.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/cart")
@CrossOrigin(origins = "*")
@Tag(name = "Cart", description = "Shopping cart operations: add, update quantity, remove, and totals")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping
    @Operation(summary = "Get user active cart")
    public ResponseEntity<CartDto> getCart(@RequestParam Long userId) {
        return ResponseEntity.ok(cartService.getCartDto(userId));
    }

    @PostMapping
    @Operation(summary = "Add a book to cart")
    public ResponseEntity<CartDto> addToCart(@RequestBody AddToCartRequest req) {
        return ResponseEntity.ok(cartService.addToCart(req));
    }

    @PutMapping("/items/{cartItemId}")
    @Operation(summary = "Update quantity of a cart item")
    public ResponseEntity<CartDto> updateCartItem(@PathVariable Long cartItemId, @RequestBody UpdateCartItemRequest req) {
        return ResponseEntity.ok(cartService.updateCartItem(cartItemId, req.getQuantity()));
    }

    @DeleteMapping("/items/{cartItemId}")
    @Operation(summary = "Remove an item from cart")
    public ResponseEntity<CartDto> removeFromCart(@PathVariable Long cartItemId) {
        return ResponseEntity.ok(cartService.removeCartItem(cartItemId));
    }
}
