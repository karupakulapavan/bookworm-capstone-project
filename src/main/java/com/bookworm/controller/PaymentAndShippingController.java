package com.bookworm.controller;

import com.bookworm.dto.DTOs.*;
import com.bookworm.service.PaymentAndShippingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
@Tag(name = "Shipping & Payments", description = "Shipping rate calculation, Payment gateways, Coupon validation")
public class PaymentAndShippingController {

    private final PaymentAndShippingService service;

    public PaymentAndShippingController(PaymentAndShippingService service) {
        this.service = service;
    }

    @PostMapping("/shipping/calculate")
    @Operation(summary = "Calculate shipping costs and tentative delivery timeline")
    public ResponseEntity<ShippingRateResponse> calculateShipping(@RequestBody ShippingRateRequest req) {
        return ResponseEntity.ok(service.calculateShipping(req));
    }

    @PostMapping("/payments/validate-coupon")
    @Operation(summary = "Validate and apply discount coupon")
    public ResponseEntity<CouponResponse> validateCoupon(@RequestBody CouponRequest req) {
        return ResponseEntity.ok(service.validateCoupon(req));
    }

    @PostMapping("/payments/process")
    @Operation(summary = "Process payment via Card, UPI, or Wallet")
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest req) {
        return ResponseEntity.ok(service.processPayment(req));
    }
}
