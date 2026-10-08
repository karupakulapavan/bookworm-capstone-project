# BookWorm E-Bookstore Backend API

## Project Overview
This project delivers the complete production-ready RESTful Backend API for the **BookWorm E-Bookstore Platform**, created using an **AI-Augmented Development Workflow with IBM BOB**.

---

## 🛠️ Tech Stack & Architecture
- **Language & Runtime:** Java 17, Spring Boot 3.2.5
- **ORM / Persistence:** Spring Data JPA / Hibernate
- **Database:** PostgreSQL (with embedded H2 compatibility)
- **API Documentation:** OpenAPI 3.0.3, Swagger UI (`springdoc-openapi`)
- **AI Agentic Tool:** IBM BOB

---

## 🚀 Getting Started

### 1. Prerequisites
- JDK 17+ installed
- Apache Maven 3.8+ installed
- PostgreSQL (or local H2 default)

### 2. Configure Database
Update [`src/main/resources/application.properties`](src/main/resources/application.properties) with your PostgreSQL credentials:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/ebookstoredb1
spring.datasource.username=postgres
spring.datasource.password=postgres
```
*Note: If PostgreSQL is not running locally, change the dialect and H2 configuration to test instantly.*

### 3. Build & Run
```bash
# Build the project
./mvnw clean install

# Run the Spring Boot application
./mvnw spring-boot:run
```

### 4. Interactive API Documentation (Swagger UI)
Once the server is running on `http://localhost:8080`, open:
👉 **[http://localhost:8080/api/v1/swagger-ui.html](http://localhost:8080/api/v1/swagger-ui.html)**
👉 OpenAPI JSON Spec: `http://localhost:8080/api/v1/v3/api-docs`

---

## 📋 Comprehensive API Endpoints Summary

### 1. Member & Authentication (`/api/v1`)
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/auth/register` | Register a new user |
| `POST` | `/auth/login` | Login user & return token + gift points |
| `GET` | `/members/{userId}/profile` | Get user details and point balance |
| `GET` | `/members/{userId}/addresses` | Fetch saved delivery addresses |
| `POST` | `/members/{userId}/addresses` | Add new shipping address |

### 2. Catalog & Books
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/catalog/categories` | List all categories (Non-Fiction, Sci-Fi, etc.) |
| `GET` | `/catalog/books` | Search, filter by category/language/price & sort |
| `GET` | `/catalog/books/{bookId}` | Book details with author bio, reviews, delivery date |
| `POST` | `/catalog/books/{bookId}/reviews`| Submit user rating and review comment |

### 3. Recommendations & Merchandising
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/recommendations/personalized?userId=1` | Personalised recommendations based on history |
| `GET` | `/recommendations/bestsellers` | Bestsellers of the month |
| `GET` | `/recommendations/new-launches` | Newly launched books |
| `GET` | `/recommendations/related/{bookId}` | Cross-sell and up-sell related books |

### 4. Shopping Cart
| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/cart?userId=1` | Get active basket with live totals, taxes & shipping |
| `POST` | `/cart` | Add book to basket |
| `PUT` | `/cart/items/{cartItemId}` | Adjust quantity |
| `DELETE` | `/cart/items/{cartItemId}` | Remove item from basket |

### 5. Shipping & Payments
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/shipping/calculate` | Calculate tentative delivery date and fee by PIN |
| `POST` | `/payments/validate-coupon` | Validate coupon codes (e.g. `SAVE100`, `BOOKWORM20`) |
| `POST` | `/payments/redeem-points` | Redeem gift points (₹1 per point) |
| `POST` | `/payments/process` | Process Card, Debit, UPI, or Wallet payment |

### 6. Orders & Checkout (with 48-hr Cancellation)
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/orders/checkout` | Convert cart into confirmed order & auto-calc invoice |
| `GET` | `/orders/user/{userId}` | Order history |
| `GET` | `/orders/{orderId}` | Single order details |
| `POST` | `/orders/{orderId}/cancel` | Cancel order (active within 48 hours of purchase) |
| `POST` | `/orders/{orderId}/buy-again?userId=1` | **Buy In Again**: re-populate cart with previous order |

---

## 🧪 Postman & cURL Testing Guide

### 1. Test Login
```bash
curl -X POST http://localhost:8080/api/v1/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email":"daniel@example.com","password":"password123"}'
```

### 2. Test Add To Cart
```bash
curl -X POST http://localhost:8080/api/v1/cart \
  -H "Content-Type: application/json" \
  -d '{"userId":1,"bookId":1,"quantity":1}'
```

### 3. Test Checkout
```bash
curl -X POST http://localhost:8080/api/v1/orders/checkout \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "addressId": 1,
    "couponCode": "SAVE100",
    "pointsRedeemed": 0,
    "paymentMethod": "CREDIT_CARD"
  }'
```

### 4. Test 48-Hour Order Cancellation
```bash
curl -X POST http://localhost:8080/api/v1/orders/1/cancel \
  -H "Content-Type: application/json" \
  -d '{"reason":"Customer changed mind"}'
```
