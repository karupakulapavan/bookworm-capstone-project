package com.bookworm.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String orderNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private String status = "CONFIRMED"; // CONFIRMED, PROCESSING, SHIPPED, DELIVERED, CANCELLED, RETURNED

    private LocalDateTime createdAt = LocalDateTime.now();

    private Integer totalItems;
    private BigDecimal itemsPrice;
    private BigDecimal tax;
    private BigDecimal deliveryCharges;
    private BigDecimal discount;
    private BigDecimal totalAmount;

    private String tentativeDeliveryDate;

    private String paymentMethod; // CREDIT_CARD, DEBIT_CARD, UPI, WALLET
    private String paymentStatus = "PAID"; // PENDING, PAID, REFUNDED
    private String transactionId;

    private LocalDateTime cancellableUntil; // createdAt + 48 hours

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id")
    private Address shippingAddress;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    public Order() {}

    @PrePersist
    public void onPrePersist() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
        if (cancellableUntil == null) {
            cancellableUntil = createdAt.plusHours(48);
        }
    }

    public boolean canCancelOrder() {
        return "CONFIRMED".equalsIgnoreCase(status) &&
                LocalDateTime.now().isBefore(cancellableUntil != null ? cancellableUntil : createdAt.plusHours(48));
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getOrderNumber() { return orderNumber; }
    public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Integer getTotalItems() { return totalItems; }
    public void setTotalItems(Integer totalItems) { this.totalItems = totalItems; }

    public BigDecimal getItemsPrice() { return itemsPrice; }
    public void setItemsPrice(BigDecimal itemsPrice) { this.itemsPrice = itemsPrice; }

    public BigDecimal getTax() { return tax; }
    public void setTax(BigDecimal tax) { this.tax = tax; }

    public BigDecimal getDeliveryCharges() { return deliveryCharges; }
    public void setDeliveryCharges(BigDecimal deliveryCharges) { this.deliveryCharges = deliveryCharges; }

    public BigDecimal getDiscount() { return discount; }
    public void setDiscount(BigDecimal discount) { this.discount = discount; }

    public BigDecimal getTotalAmount() { return totalAmount; }
    public void setTotalAmount(BigDecimal totalAmount) { this.totalAmount = totalAmount; }

    public String getTentativeDeliveryDate() { return tentativeDeliveryDate; }
    public void setTentativeDeliveryDate(String tentativeDeliveryDate) { this.tentativeDeliveryDate = tentativeDeliveryDate; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getPaymentStatus() { return paymentStatus; }
    public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }

    public String getTransactionId() { return transactionId; }
    public void setTransactionId(String transactionId) { this.transactionId = transactionId; }

    public LocalDateTime getCancellableUntil() { return cancellableUntil; }
    public void setCancellableUntil(LocalDateTime cancellableUntil) { this.cancellableUntil = cancellableUntil; }

    public Address getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(Address shippingAddress) { this.shippingAddress = shippingAddress; }

    public List<OrderItem> getItems() { return items; }
    public void setItems(List<OrderItem> items) { this.items = items; }
}
