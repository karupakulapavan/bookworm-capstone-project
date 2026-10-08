-- ==============================================================================
-- BookWorm E-Bookstore Database Schema (PostgreSQL)
-- Database Name: ebookstoredb1
-- ==============================================================================

-- 1. Create Database (Run this as postgres superuser if the DB doesn't exist)
-- CREATE DATABASE ebookstoredb1;

-- Connect to ebookstoredb1 before running subsequent statements
-- \c ebookstoredb1;

-- Clean existing tables (in correct foreign key order)
DROP TABLE IF EXISTS reviews CASCADE;
DROP TABLE IF EXISTS order_items CASCADE;
DROP TABLE IF EXISTS orders CASCADE;
DROP TABLE IF EXISTS cart_items CASCADE;
DROP TABLE IF EXISTS carts CASCADE;
DROP TABLE IF EXISTS books CASCADE;
DROP TABLE IF EXISTS categories CASCADE;
DROP TABLE IF EXISTS addresses CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- ------------------------------------------------------------------------------
-- 1. USERS TABLE
-- ------------------------------------------------------------------------------
CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    full_name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    phone VARCHAR(50),
    gift_points INT DEFAULT 250,
    role VARCHAR(50) DEFAULT 'REGISTERED_USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_users_email ON users(email);

-- ------------------------------------------------------------------------------
-- 2. ADDRESSES TABLE
-- ------------------------------------------------------------------------------
CREATE TABLE addresses (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    first_name VARCHAR(100),
    last_name VARCHAR(100),
    address_line VARCHAR(255) NOT NULL,
    city VARCHAR(100) NOT NULL,
    state VARCHAR(100) NOT NULL,
    pin VARCHAR(20) NOT NULL,
    country VARCHAR(100) DEFAULT 'India',
    phone VARCHAR(50),
    email VARCHAR(255),
    is_default BOOLEAN DEFAULT FALSE
);

CREATE INDEX idx_addresses_user_id ON addresses(user_id);

-- ------------------------------------------------------------------------------
-- 3. CATEGORIES TABLE
-- ------------------------------------------------------------------------------
CREATE TABLE categories (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    slug VARCHAR(100) NOT NULL UNIQUE,
    description TEXT
);

-- ------------------------------------------------------------------------------
-- 4. BOOKS TABLE
-- ------------------------------------------------------------------------------
CREATE TABLE books (
    id BIGSERIAL PRIMARY KEY,
    category_id BIGINT REFERENCES categories(id) ON DELETE SET NULL,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    author_bio TEXT,
    publisher VARCHAR(255),
    description TEXT,
    price NUMERIC(10, 2) NOT NULL,
    format VARCHAR(50) DEFAULT 'Paperback', -- Paperback, Hardcover, eBook
    language VARCHAR(50) DEFAULT 'English',
    rating NUMERIC(2, 1) DEFAULT 4.5,
    review_count INT DEFAULT 0,
    copies_sold INT DEFAULT 0,
    image_url VARCHAR(500),
    is_bestseller BOOLEAN DEFAULT FALSE,
    is_new_launch BOOLEAN DEFAULT FALSE,
    tentative_delivery_date VARCHAR(50) DEFAULT 'Mon, 21 Jul',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_books_category ON books(category_id);
CREATE INDEX idx_books_rating ON books(rating);
CREATE INDEX idx_books_price ON books(price);

-- ------------------------------------------------------------------------------
-- 5. REVIEWS TABLE
-- ------------------------------------------------------------------------------
CREATE TABLE reviews (
    id BIGSERIAL PRIMARY KEY,
    book_id BIGINT REFERENCES books(id) ON DELETE CASCADE,
    reviewer_name VARCHAR(100) NOT NULL,
    rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),
    comment TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX idx_reviews_book_id ON reviews(book_id);

-- ------------------------------------------------------------------------------
-- 6. CARTS & CART_ITEMS TABLES
-- ------------------------------------------------------------------------------
CREATE TABLE carts (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE UNIQUE
);

CREATE TABLE cart_items (
    id BIGSERIAL PRIMARY KEY,
    cart_id BIGINT REFERENCES carts(id) ON DELETE CASCADE,
    book_id BIGINT REFERENCES books(id) ON DELETE CASCADE,
    quantity INT NOT NULL DEFAULT 1 CHECK (quantity > 0),
    CONSTRAINT uq_cart_book UNIQUE (cart_id, book_id)
);

CREATE INDEX idx_cart_items_cart_id ON cart_items(cart_id);

-- ------------------------------------------------------------------------------
-- 7. ORDERS & ORDER_ITEMS TABLES
-- ------------------------------------------------------------------------------
CREATE TABLE orders (
    id BIGSERIAL PRIMARY KEY,
    order_number VARCHAR(100) NOT NULL UNIQUE,
    user_id BIGINT REFERENCES users(id) ON DELETE CASCADE,
    address_id BIGINT REFERENCES addresses(id) ON DELETE SET NULL,
    status VARCHAR(50) DEFAULT 'CONFIRMED', -- CONFIRMED, PROCESSING, SHIPPED, DELIVERED, CANCELLED, RETURNED
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    cancellable_until TIMESTAMP NOT NULL, -- createdAt + 48 hours
    total_items INT NOT NULL,
    items_price NUMERIC(10, 2) NOT NULL,
    tax NUMERIC(10, 2) NOT NULL,
    delivery_charges NUMERIC(10, 2) NOT NULL,
    discount NUMERIC(10, 2) DEFAULT 0.00,
    total_amount NUMERIC(10, 2) NOT NULL,
    tentative_delivery_date VARCHAR(50),
    payment_method VARCHAR(50), -- CREDIT_CARD, DEBIT_CARD, UPI, WALLET
    payment_status VARCHAR(50) DEFAULT 'PAID', -- PAID, PENDING, REFUNDED
    transaction_id VARCHAR(100)
);

CREATE INDEX idx_orders_user_id ON orders(user_id);
CREATE INDEX idx_orders_order_number ON orders(order_number);

CREATE TABLE order_items (
    id BIGSERIAL PRIMARY KEY,
    order_id BIGINT REFERENCES orders(id) ON DELETE CASCADE,
    book_id BIGINT REFERENCES books(id) ON DELETE SET NULL,
    quantity INT NOT NULL,
    price NUMERIC(10, 2) NOT NULL
);

CREATE INDEX idx_order_items_order_id ON order_items(order_id);
