package com.bookworm.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class DTOs {

    public static class RegisterRequest {
        private String fullName;
        private String email;
        private String password;
        private String phone;

        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
    }

    public static class LoginRequest {
        private String email;
        private String password;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }

    public static class AuthResponse {
        private String token;
        private Long userId;
        private String fullName;
        private String email;
        private Integer giftPoints;

        public AuthResponse() {}
        public AuthResponse(String token, Long userId, String fullName, String email, Integer giftPoints) {
            this.token = token;
            this.userId = userId;
            this.fullName = fullName;
            this.email = email;
            this.giftPoints = giftPoints;
        }

        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public Integer getGiftPoints() { return giftPoints; }
        public void setGiftPoints(Integer giftPoints) { this.giftPoints = giftPoints; }
    }

    public static class UserResponse {
        private Long id;
        private String fullName;
        private String email;
        private String phone;
        private Integer giftPoints;
        private String role;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getFullName() { return fullName; }
        public void setFullName(String fullName) { this.fullName = fullName; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public Integer getGiftPoints() { return giftPoints; }
        public void setGiftPoints(Integer giftPoints) { this.giftPoints = giftPoints; }
        public String getRole() { return role; }
        public void setRole(String role) { this.role = role; }
    }

    public static class AddressDto {
        private Long id;
        private String firstName;
        private String lastName;
        private String addressLine;
        private String city;
        private String state;
        private String pin;
        private String country;
        private String phone;
        private String email;
        private Boolean isDefault;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getFirstName() { return firstName; }
        public void setFirstName(String firstName) { this.firstName = firstName; }
        public String getLastName() { return lastName; }
        public void setLastName(String lastName) { this.lastName = lastName; }
        public String getAddressLine() { return addressLine; }
        public void setAddressLine(String addressLine) { this.addressLine = addressLine; }
        public String getCity() { return city; }
        public void setCity(String city) { this.city = city; }
        public String getState() { return state; }
        public void setState(String state) { this.state = state; }
        public String getPin() { return pin; }
        public void setPin(String pin) { this.pin = pin; }
        public String getCountry() { return country; }
        public void setCountry(String country) { this.country = country; }
        public String getPhone() { return phone; }
        public void setPhone(String phone) { this.phone = phone; }
        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public Boolean getIsDefault() { return isDefault; }
        public void setIsDefault(Boolean isDefault) { this.isDefault = isDefault; }
    }

    public static class CategoryDto {
        private Long id;
        private String name;
        private String slug;
        private String description;

        public CategoryDto() {}
        public CategoryDto(Long id, String name, String slug, String description) {
            this.id = id;
            this.name = name;
            this.slug = slug;
            this.description = description;
        }

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        public String getSlug() { return slug; }
        public void setSlug(String slug) { this.slug = slug; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
    }

    public static class BookDto {
        private Long id;
        private String title;
        private String author;
        private String publisher;
        private String categoryName;
        private BigDecimal price;
        private String format;
        private String language;
        private Double rating;
        private Integer reviewCount;
        private Integer copiesSold;
        private String imageUrl;
        private String tentativeDeliveryDate;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getAuthor() { return author; }
        public void setAuthor(String author) { this.author = author; }
        public String getPublisher() { return publisher; }
        public void setPublisher(String publisher) { this.publisher = publisher; }
        public String getCategoryName() { return categoryName; }
        public void setCategoryName(String categoryName) { this.categoryName = categoryName; }
        public BigDecimal getPrice() { return price; }
        public void setPrice(BigDecimal price) { this.price = price; }
        public String getFormat() { return format; }
        public void setFormat(String format) { this.format = format; }
        public String getLanguage() { return language; }
        public void setLanguage(String language) { this.language = language; }
        public Double getRating() { return rating; }
        public void setRating(Double rating) { this.rating = rating; }
        public Integer getReviewCount() { return reviewCount; }
        public void setReviewCount(Integer reviewCount) { this.reviewCount = reviewCount; }
        public Integer getCopiesSold() { return copiesSold; }
        public void setCopiesSold(Integer copiesSold) { this.copiesSold = copiesSold; }
        public String getImageUrl() { return imageUrl; }
        public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
        public String getTentativeDeliveryDate() { return tentativeDeliveryDate; }
        public void setTentativeDeliveryDate(String tentativeDeliveryDate) { this.tentativeDeliveryDate = tentativeDeliveryDate; }
    }

    public static class BookDetailDto extends BookDto {
        private String authorBio;
        private String description;
        private List<ReviewDto> reviews;

        public String getAuthorBio() { return authorBio; }
        public void setAuthorBio(String authorBio) { this.authorBio = authorBio; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public List<ReviewDto> getReviews() { return reviews; }
        public void setReviews(List<ReviewDto> reviews) { this.reviews = reviews; }
    }

    public static class ReviewRequest {
        private String reviewerName;
        private Integer rating;
        private String comment;

        public String getReviewerName() { return reviewerName; }
        public void setReviewerName(String reviewerName) { this.reviewerName = reviewerName; }
        public Integer getRating() { return rating; }
        public void setRating(Integer rating) { this.rating = rating; }
        public String getComment() { return comment; }
        public void setComment(String comment) { this.comment = comment; }
    }

    public static class ReviewDto {
        private Long id;
        private String reviewerName;
        private Integer rating;
        private String comment;
        private LocalDateTime createdAt;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public String getReviewerName() { return reviewerName; }
        public void setReviewerName(String reviewerName) { this.reviewerName = reviewerName; }
        public Integer getRating() { return rating; }
        public void setRating(Integer rating) { this.rating = rating; }
        public String getComment() { return comment; }
        public void setComment(String comment) { this.comment = comment; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    }

    public static class AddToCartRequest {
        private Long userId;
        private Long bookId;
        private Integer quantity;

        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        public Long getBookId() { return bookId; }
        public void setBookId(Long bookId) { this.bookId = bookId; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
    }

    public static class UpdateCartItemRequest {
        private Integer quantity;
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
    }

    public static class CartItemDto {
        private Long id;
        private Long bookId;
        private String title;
        private String author;
        private String format;
        private BigDecimal price;
        private Integer quantity;
        private BigDecimal subTotal;
        private String imageUrl;
        private String deliveryDate;

        public Long getId() { return id; }
        public void setId(Long id) { this.id = id; }
        public Long getBookId() { return bookId; }
        public void setBookId(Long bookId) { this.bookId = bookId; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getAuthor() { return author; }
        public void setAuthor(String author) { this.author = author; }
        public String getFormat() { return format; }
        public void setFormat(String format) { this.format = format; }
        public BigDecimal getPrice() { return price; }
        public void setPrice(BigDecimal price) { this.price = price; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
        public BigDecimal getSubTotal() { return subTotal; }
        public void setSubTotal(BigDecimal subTotal) { this.subTotal = subTotal; }
        public String getImageUrl() { return imageUrl; }
        public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
        public String getDeliveryDate() { return deliveryDate; }
        public void setDeliveryDate(String deliveryDate) { this.deliveryDate = deliveryDate; }
    }

    public static class CartDto {
        private Long cartId;
        private Long userId;
        private List<CartItemDto> items;
        private Integer itemCount;
        private BigDecimal totalPrice;
        private BigDecimal tax;
        private BigDecimal deliveryCharges;
        private BigDecimal discount;
        private BigDecimal grandTotal;

        public Long getCartId() { return cartId; }
        public void setCartId(Long cartId) { this.cartId = cartId; }
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        public List<CartItemDto> getItems() { return items; }
        public void setItems(List<CartItemDto> items) { this.items = items; }
        public Integer getItemCount() { return itemCount; }
        public void setItemCount(Integer itemCount) { this.itemCount = itemCount; }
        public BigDecimal getTotalPrice() { return totalPrice; }
        public void setTotalPrice(BigDecimal totalPrice) { this.totalPrice = totalPrice; }
        public BigDecimal getTax() { return tax; }
        public void setTax(BigDecimal tax) { this.tax = tax; }
        public BigDecimal getDeliveryCharges() { return deliveryCharges; }
        public void setDeliveryCharges(BigDecimal deliveryCharges) { this.deliveryCharges = deliveryCharges; }
        public BigDecimal getDiscount() { return discount; }
        public void setDiscount(BigDecimal discount) { this.discount = discount; }
        public BigDecimal getGrandTotal() { return grandTotal; }
        public void setGrandTotal(BigDecimal grandTotal) { this.grandTotal = grandTotal; }
    }

    public static class ShippingRateRequest {
        private String pin;
        private BigDecimal cartTotal;

        public String getPin() { return pin; }
        public void setPin(String pin) { this.pin = pin; }
        public BigDecimal getCartTotal() { return cartTotal; }
        public void setCartTotal(BigDecimal cartTotal) { this.cartTotal = cartTotal; }
    }

    public static class ShippingRateResponse {
        private BigDecimal shippingCost;
        private Boolean isFreeDelivery;
        private Integer estimatedDays;
        private String tentativeDeliveryDate;

        public ShippingRateResponse() {}
        public ShippingRateResponse(BigDecimal shippingCost, Boolean isFreeDelivery, Integer estimatedDays, String tentativeDeliveryDate) {
            this.shippingCost = shippingCost;
            this.isFreeDelivery = isFreeDelivery;
            this.estimatedDays = estimatedDays;
            this.tentativeDeliveryDate = tentativeDeliveryDate;
        }

        public BigDecimal getShippingCost() { return shippingCost; }
        public void setShippingCost(BigDecimal shippingCost) { this.shippingCost = shippingCost; }
        public Boolean getIsFreeDelivery() { return isFreeDelivery; }
        public void setIsFreeDelivery(Boolean isFreeDelivery) { this.isFreeDelivery = isFreeDelivery; }
        public Integer getEstimatedDays() { return estimatedDays; }
        public void setEstimatedDays(Integer estimatedDays) { this.estimatedDays = estimatedDays; }
        public String getTentativeDeliveryDate() { return tentativeDeliveryDate; }
        public void setTentativeDeliveryDate(String tentativeDeliveryDate) { this.tentativeDeliveryDate = tentativeDeliveryDate; }
    }

    public static class CouponRequest {
        private String couponCode;
        private BigDecimal cartTotal;

        public String getCouponCode() { return couponCode; }
        public void setCouponCode(String couponCode) { this.couponCode = couponCode; }
        public BigDecimal getCartTotal() { return cartTotal; }
        public void setCartTotal(BigDecimal cartTotal) { this.cartTotal = cartTotal; }
    }

    public static class CouponResponse {
        private String couponCode;
        private BigDecimal discountAmount;
        private Boolean isValid;
        private String message;

        public CouponResponse() {}
        public CouponResponse(String couponCode, BigDecimal discountAmount, Boolean isValid, String message) {
            this.couponCode = couponCode;
            this.discountAmount = discountAmount;
            this.isValid = isValid;
            this.message = message;
        }

        public String getCouponCode() { return couponCode; }
        public void setCouponCode(String couponCode) { this.couponCode = couponCode; }
        public BigDecimal getDiscountAmount() { return discountAmount; }
        public void setDiscountAmount(BigDecimal discountAmount) { this.discountAmount = discountAmount; }
        public Boolean getIsValid() { return isValid; }
        public void setIsValid(Boolean isValid) { this.isValid = isValid; }
        public String getMessage() { return message; }
        public void setMessage(String message) { this.message = message; }
    }

    public static class RedeemPointsRequest {
        private Long userId;
        private Integer pointsToRedeem;
        private BigDecimal cartTotal;

        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        public Integer getPointsToRedeem() { return pointsToRedeem; }
        public void setPointsToRedeem(Integer pointsToRedeem) { this.pointsToRedeem = pointsToRedeem; }
        public BigDecimal getCartTotal() { return cartTotal; }
        public void setCartTotal(BigDecimal cartTotal) { this.cartTotal = cartTotal; }
    }

    public static class RedeemPointsResponse {
        private Integer pointsRedeemed;
        private BigDecimal discountValue;
        private Integer remainingPoints;
        private String status;

        public RedeemPointsResponse() {}
        public RedeemPointsResponse(Integer pointsRedeemed, BigDecimal discountValue, Integer remainingPoints, String status) {
            this.pointsRedeemed = pointsRedeemed;
            this.discountValue = discountValue;
            this.remainingPoints = remainingPoints;
            this.status = status;
        }

        public Integer getPointsRedeemed() { return pointsRedeemed; }
        public void setPointsRedeemed(Integer pointsRedeemed) { this.pointsRedeemed = pointsRedeemed; }
        public BigDecimal getDiscountValue() { return discountValue; }
        public void setDiscountValue(BigDecimal discountValue) { this.discountValue = discountValue; }
        public Integer getRemainingPoints() { return remainingPoints; }
        public void setRemainingPoints(Integer remainingPoints) { this.remainingPoints = remainingPoints; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
    }

    public static class PaymentRequest {
        private Long orderId;
        private String paymentMethod;
        private String cardNumber;
        private String cardHolderName;
        private String cvv;
        private String expiryDate;
        private String upiId;
        private BigDecimal amount;

        public Long getOrderId() { return orderId; }
        public void setOrderId(Long orderId) { this.orderId = orderId; }
        public String getPaymentMethod() { return paymentMethod; }
        public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
        public String getCardNumber() { return cardNumber; }
        public void setCardNumber(String cardNumber) { this.cardNumber = cardNumber; }
        public String getCardHolderName() { return cardHolderName; }
        public void setCardHolderName(String cardHolderName) { this.cardHolderName = cardHolderName; }
        public String getCvv() { return cvv; }
        public void setCvv(String cvv) { this.cvv = cvv; }
        public String getExpiryDate() { return expiryDate; }
        public void setExpiryDate(String expiryDate) { this.expiryDate = expiryDate; }
        public String getUpiId() { return upiId; }
        public void setUpiId(String upiId) { this.upiId = upiId; }
        public BigDecimal getAmount() { return amount; }
        public void setAmount(BigDecimal amount) { this.amount = amount; }
    }

    public static class PaymentResponse {
        private String transactionId;
        private String status;
        private String paymentMethod;
        private BigDecimal amountPaid;
        private LocalDateTime timestamp;

        public PaymentResponse() {}
        public PaymentResponse(String transactionId, String status, String paymentMethod, BigDecimal amountPaid, LocalDateTime timestamp) {
            this.transactionId = transactionId;
            this.status = status;
            this.paymentMethod = paymentMethod;
            this.amountPaid = amountPaid;
            this.timestamp = timestamp;
        }

        public String getTransactionId() { return transactionId; }
        public void setTransactionId(String transactionId) { this.transactionId = transactionId; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getPaymentMethod() { return paymentMethod; }
        public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
        public BigDecimal getAmountPaid() { return amountPaid; }
        public void setAmountPaid(BigDecimal amountPaid) { this.amountPaid = amountPaid; }
        public LocalDateTime getTimestamp() { return timestamp; }
        public void setTimestamp(LocalDateTime timestamp) { this.timestamp = timestamp; }
    }

    public static class CheckoutRequest {
        private Long userId;
        private Long addressId;
        private String couponCode;
        private Integer pointsRedeemed;
        private String paymentMethod;

        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
        public Long getAddressId() { return addressId; }
        public void setAddressId(Long addressId) { this.addressId = addressId; }
        public String getCouponCode() { return couponCode; }
        public void setCouponCode(String couponCode) { this.couponCode = couponCode; }
        public Integer getPointsRedeemed() { return pointsRedeemed; }
        public void setPointsRedeemed(Integer pointsRedeemed) { this.pointsRedeemed = pointsRedeemed; }
        public String getPaymentMethod() { return paymentMethod; }
        public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    }

    public static class OrderItemDto {
        private Long bookId;
        private String title;
        private String author;
        private BigDecimal price;
        private Integer quantity;
        private String format;
        private String imageUrl;

        public Long getBookId() { return bookId; }
        public void setBookId(Long bookId) { this.bookId = bookId; }
        public String getTitle() { return title; }
        public void setTitle(String title) { this.title = title; }
        public String getAuthor() { return author; }
        public void setAuthor(String author) { this.author = author; }
        public BigDecimal getPrice() { return price; }
        public void setPrice(BigDecimal price) { this.price = price; }
        public Integer getQuantity() { return quantity; }
        public void setQuantity(Integer quantity) { this.quantity = quantity; }
        public String getFormat() { return format; }
        public void setFormat(String format) { this.format = format; }
        public String getImageUrl() { return imageUrl; }
        public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
    }

    public static class OrderResponse {
        private Long orderId;
        private String orderNumber;
        private Long userId;
        private String status;
        private LocalDateTime createdAt;
        private Integer totalItems;
        private BigDecimal itemsPrice;
        private BigDecimal tax;
        private BigDecimal deliveryCharges;
        private BigDecimal discount;
        private BigDecimal totalAmount;
        private String tentativeDeliveryDate;
        private String paymentStatus;
        private LocalDateTime cancellableUntil;
        private Boolean canCancel;
        private AddressDto shippingAddress;
        private List<OrderItemDto> items;

        public Long getOrderId() { return orderId; }
        public void setOrderId(Long orderId) { this.orderId = orderId; }
        public String getOrderNumber() { return orderNumber; }
        public void setOrderNumber(String orderNumber) { this.orderNumber = orderNumber; }
        public Long getUserId() { return userId; }
        public void setUserId(Long userId) { this.userId = userId; }
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
        public String getPaymentStatus() { return paymentStatus; }
        public void setPaymentStatus(String paymentStatus) { this.paymentStatus = paymentStatus; }
        public LocalDateTime getCancellableUntil() { return cancellableUntil; }
        public void setCancellableUntil(LocalDateTime cancellableUntil) { this.cancellableUntil = cancellableUntil; }
        public Boolean getCanCancel() { return canCancel; }
        public void setCanCancel(Boolean canCancel) { this.canCancel = canCancel; }
        public AddressDto getShippingAddress() { return shippingAddress; }
        public void setShippingAddress(AddressDto shippingAddress) { this.shippingAddress = shippingAddress; }
        public List<OrderItemDto> getItems() { return items; }
        public void setItems(List<OrderItemDto> items) { this.items = items; }
    }

    public static class CancelOrderRequest {
        private String reason;
        public String getReason() { return reason; }
        public void setReason(String reason) { this.reason = reason; }
    }
}
