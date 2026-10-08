-- ==============================================================================
-- BookWorm E-Bookstore Seed Data (PostgreSQL)
-- Matches Capstone Wireframes & Mockup Requirements
-- ==============================================================================

-- 1. Insert Categories
INSERT INTO categories (name, slug, description) VALUES
('Non-Fiction', 'non-fiction', 'Practical guides, self-growth, productivity, and lifelong learning.'),
('Fiction', 'fiction', 'Gripping novels, thrillers, drama, and bestselling stories.'),
('Sci-Fi & Fantasy', 'sci-fi', 'Futuristic adventures, space exploration, and speculative worlds.'),
('Romance', 'romance', 'Heartwarming stories of love, passion, and personal discovery.'),
('Children', 'children', 'Delightful illustrated tales and adventure stories for young minds.')
ON CONFLICT (name) DO NOTHING;

-- 2. Insert Users
INSERT INTO users (full_name, email, password, phone, gift_points, role) VALUES
('Daniel Reed', 'daniel@example.com', 'password123', '+91 1234567890', 250, 'REGISTERED_USER'),
('Alice Walker', 'alice@example.com', 'secret456', '+91 9876543210', 100, 'REGISTERED_USER')
ON CONFLICT (email) DO NOTHING;

-- 3. Insert Addresses
INSERT INTO addresses (user_id, first_name, last_name, address_line, city, state, pin, country, phone, email, is_default) VALUES
(1, 'Daniel', 'Reed', 'Address Line 2, Tech Park Boulevard', 'Mumbai', 'Maharashtra', '400001', 'India', '+91 1234567890', 'daniel@example.com', TRUE),
(2, 'Alice', 'Walker', 'Flat 402, Green Meadows', 'Bengaluru', 'Karnataka', '560001', 'India', '+91 9876543210', 'alice@example.com', TRUE);

-- 4. Insert Books (From Wireframe Slides)
INSERT INTO books (category_id, title, author, author_bio, publisher, description, price, format, language, rating, review_count, copies_sold, image_url, is_bestseller, is_new_launch, tentative_delivery_date) VALUES
(1, 'The Joy of Minimalism', 'Daniel Reed', 'Daniel Reed is a writer, minimalist, and productivity coach based in San Francisco. With a passion for intentional living, Daniel has dedicated his career to helping individuals simplify their lives.', 'ABC Publishers', 'Discover how less can truly be more. In The Joy of Minimalism, Daniel Reed guides you through practical strategies to declutter your mind, space, and schedule.', 149.00, 'Paperback', 'English', 4.9, 145, 145, '/images/joy-of-minimalism.png', FALSE, TRUE, 'Mon, 21 Jul'),
(1, 'The Path to Success', 'James Wright', 'James Wright is an executive leadership coach and bestselling author.', 'Global Press', 'A practical guide to achieving goals with clarity and confidence.', 359.00, 'Paperback', 'English', 4.8, 89, 320, '/images/path-to-success.png', TRUE, FALSE, 'Mon, 21 Jul'),
(1, 'The Art of Focus', 'Arjun Patel', 'Arjun Patel is a cognitive science researcher.', 'Apex Books', 'Practical guide to mastering focus & boosting productivity every day.', 399.00, 'Paperback', 'English', 4.7, 210, 540, '/images/art-of-focus.png', TRUE, FALSE, 'Mon, 21 Jul'),
(1, 'The Art of Learning', 'Raj Patel', 'Raj Patel is an educator and memory champion.', 'Zenith Publications', 'Master the mindset and methods for effective lifelong learning.', 259.00, 'Paperback', 'English', 4.6, 94, 180, '/images/art-of-learning.png', TRUE, FALSE, 'Mon, 21 Jul'),
(2, 'The Midnight Hour', 'James Adams', 'James Adams is a critically acclaimed suspense novelist.', 'Horror House', 'Haunting tale of a man''s journey & the shadows of a forgotten past.', 299.00, 'Paperback', 'English', 4.5, 78, 210, '/images/midnight-hour.png', TRUE, FALSE, 'Mon, 21 Jul'),
(4, 'Beneath the Stars', 'Jessica Martin', 'Jessica Martin is an award-winning romance author.', 'Starlight Romance', 'A heartwarming romance where two souls discover what they need most.', 499.00, 'Hardcover', 'English', 4.9, 112, 340, '/images/beneath-the-stars.png', TRUE, FALSE, 'Mon, 21 Jul'),
(3, 'The Final Frontier', 'Laura Mitchell', 'Laura Mitchell writes science-fiction epics.', 'Orion Sci-Fi', 'A mission to space secrets to change humanity forever.', 359.00, 'Paperback', 'English', 4.7, 67, 190, '/images/final-frontier.png', TRUE, FALSE, 'Mon, 21 Jul'),
(2, 'The Vanishing House', 'Clara Nelson', 'Clara Nelson is a renowned gothic mystery author.', 'Mystery Works', 'A chilling mystery unfolds within a house that disappears.', 99.00, 'eBook', 'English', 4.4, 52, 400, '/images/vanishing-house.png', FALSE, TRUE, 'Mon, 21 Jul'),
(5, 'The Lost Kitten', 'Emily Parker', 'Emily Parker writes beloved illustrated children''s books.', 'Kids Corner', 'A heartwarming tale of courage, friendship, and feline adventure.', 339.00, 'Hardcover', 'English', 4.9, 48, 150, '/images/lost-kitten.png', FALSE, TRUE, 'Mon, 21 Jul');

-- 5. Insert Sample Review
INSERT INTO reviews (book_id, reviewer_name, rating, comment) VALUES
(1, 'John Smith', 5, 'The accordion component delivers large amounts of content in a small space through progressive disclosure. The user gets key details about the underlying content.');

-- 6. Insert Cart for User 1
INSERT INTO carts (user_id) VALUES (1) ON CONFLICT (user_id) DO NOTHING;

-- 7. Insert Sample Active Cart Items (Joy of Minimalism + Path to Success)
INSERT INTO cart_items (cart_id, book_id, quantity) VALUES
(1, 1, 1),
(1, 2, 1)
ON CONFLICT (cart_id, book_id) DO NOTHING;
