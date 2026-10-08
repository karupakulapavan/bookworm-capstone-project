package com.bookworm.service;

import com.bookworm.dto.DTOs.*;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Service
public class PaymentAndShippingService {

    public ShippingRateResponse calculateShipping(ShippingRateRequest req) {
        BigDecimal cartTotal = req.getCartTotal() != null ? req.getCartTotal() : BigDecimal.ZERO;
        boolean isFree = cartTotal.compareTo(new BigDecimal("300.00")) >= 0;
        BigDecimal cost = isFree ? BigDecimal.ZERO : new BigDecimal("50.00");
        int days = 3;

        LocalDate arrivalDate = LocalDate.now().plusDays(days);
        String tentativeDate = arrivalDate.format(DateTimeFormatter.ofPattern("EEE, dd MMM"));

        return new ShippingRateResponse(cost, isFree, days, tentativeDate);
    }

    public CouponResponse validateCoupon(CouponRequest req) {
        String code = req.getCouponCode() != null ? req.getCouponCode().trim().toUpperCase() : "";
        BigDecimal cartTotal = req.getCartTotal() != null ? req.getCartTotal() : BigDecimal.ZERO;

        if ("SAVE100".equals(code)) {
            if (cartTotal.compareTo(new BigDecimal("300.00")) >= 0) {
                return new CouponResponse(code, new BigDecimal("100.00"), true, "Coupon SAVE100 applied: ₹100 discount");
            } else {
                return new CouponResponse(code, BigDecimal.ZERO, false, "Minimum cart amount ₹300 required for SAVE100");
            }
        } else if ("BOOKWORM20".equals(code)) {
            BigDecimal discount = cartTotal.multiply(new BigDecimal("0.20")).setScale(2, java.math.RoundingMode.HALF_UP);
            return new CouponResponse(code, discount, true, "20% Discount applied!");
        }

        return new CouponResponse(code, BigDecimal.ZERO, false, "Invalid or expired coupon code");
    }

    public PaymentResponse processPayment(PaymentRequest req) {
        String txnId = "TXN-" + UUID.randomUUID().toString().substring(0, 10).toUpperCase();
        return new PaymentResponse(
                txnId,
                "SUCCESS",
                req.getPaymentMethod() != null ? req.getPaymentMethod() : "CREDIT_CARD",
                req.getAmount(),
                java.time.LocalDateTime.now()
        );
    }
}
