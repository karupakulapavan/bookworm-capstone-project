package com.bookworm.config;

import com.bookworm.model.*;
import com.bookworm.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(
            CategoryRepository categoryRepo,
            BookRepository bookRepo,
            UserRepository userRepo,
            AddressRepository addressRepo,
            ReviewRepository reviewRepo) {
        return args -> {
            // 0. Ensure default user exists
            if (userRepo.count() == 0) {
                User user = new User("Daniel Reed", "daniel@example.com", "password123", "+91 1234567890");
                user.setGiftPoints(250);
                user = userRepo.save(user);

                Address addr = new Address();
                addr.setFirstName("Daniel");
                addr.setLastName("Reed");
                addr.setAddressLine("Address Line 2, Tech Park Boulevard");
                addr.setCity("Mumbai");
                addr.setState("Maharashtra");
                addr.setPin("400001");
                addr.setCountry("India");
                addr.setPhone("+91 1234567890");
                addr.setEmail("daniel@example.com");
                addr.setIsDefault(true);
                addr.setUser(user);
                addressRepo.save(addr);
            }

            if (categoryRepo.count() > 0) return;

            // 1. Categories
            Category c1 = categoryRepo.save(new Category("Non-Fiction", "non-fiction", "Real-world stories, self growth and learning"));
            Category c2 = categoryRepo.save(new Category("Fiction", "fiction", "Gripping stories, novels and thrillers"));
            Category c3 = categoryRepo.save(new Category("Sci-Fi & Fantasy", "sci-fi", "Futuristic adventures and speculative fiction"));
            Category c4 = categoryRepo.save(new Category("Romance", "romance", "Heartwarming love stories and emotional journeys"));
            Category c5 = categoryRepo.save(new Category("Children", "children", "Illustrated stories and learning books for young minds"));

            // 2. Books from Wireframe
            Book b1 = new Book();
            b1.setTitle("The Joy of Minimalism");
            b1.setAuthor("Daniel Reed");
            b1.setAuthorBio("Daniel Reed is a writer, minimalist, and productivity coach based in San Francisco. With a passion for intentional living, Daniel has dedicated his career to helping individuals simplify their lives.");
            b1.setPublisher("ABC Publishers");
            b1.setDescription("Discover how less can truly be more. In The Joy of Minimalism, Daniel Reed guides you through practical strategies to declutter your mind, space, and schedule.");
            b1.setPrice(new BigDecimal("149.00"));
            b1.setFormat("Paperback");
            b1.setLanguage("English");
            b1.setRating(4.9);
            b1.setReviewCount(145);
            b1.setCopiesSold(145);
            b1.setIsBestseller(false);
            b1.setIsNewLaunch(true);
            b1.setImageUrl("/images/joy-of-minimalism.png");
            b1.setCategory(c1);
            b1.setTentativeDeliveryDate("Mon, 21 Jul");
            b1 = bookRepo.save(b1);

            Book b2 = new Book();
            b2.setTitle("The Path to Success");
            b2.setAuthor("James Wright");
            b2.setAuthorBio("James Wright is an executive leadership coach and bestselling author.");
            b2.setPublisher("Global Press");
            b2.setDescription("A practical guide to achieving goals with clarity and confidence.");
            b2.setPrice(new BigDecimal("359.00"));
            b2.setFormat("Paperback");
            b2.setLanguage("English");
            b2.setRating(4.8);
            b2.setReviewCount(89);
            b2.setCopiesSold(320);
            b2.setIsBestseller(true);
            b2.setIsNewLaunch(false);
            b2.setImageUrl("/images/path-to-success.png");
            b2.setCategory(c1);
            b2.setTentativeDeliveryDate("Mon, 21 Jul");
            b2 = bookRepo.save(b2);

            Book b3 = new Book();
            b3.setTitle("The Art of Focus");
            b3.setAuthor("Arjun Patel");
            b3.setPublisher("Apex Books");
            b3.setDescription("Practical guide to mastering focus & boosting productivity every day.");
            b3.setPrice(new BigDecimal("399.00"));
            b3.setFormat("Paperback");
            b3.setLanguage("English");
            b3.setRating(4.7);
            b3.setReviewCount(210);
            b3.setCopiesSold(540);
            b3.setIsBestseller(true);
            b3.setIsNewLaunch(false);
            b3.setImageUrl("/images/art-of-focus.png");
            b3.setCategory(c1);
            b3.setTentativeDeliveryDate("Mon, 21 Jul");
            bookRepo.save(b3);

            Book b4 = new Book();
            b4.setTitle("The Art of Learning");
            b4.setAuthor("Raj Patel");
            b4.setPublisher("Zenith Publications");
            b4.setDescription("Master the mindset and methods for effective lifelong learning.");
            b4.setPrice(new BigDecimal("259.00"));
            b4.setFormat("Paperback");
            b4.setLanguage("English");
            b4.setRating(4.6);
            b4.setReviewCount(94);
            b4.setCopiesSold(180);
            b4.setIsBestseller(true);
            b4.setIsNewLaunch(false);
            b4.setImageUrl("/images/art-of-learning.png");
            b4.setCategory(c1);
            b4.setTentativeDeliveryDate("Mon, 21 Jul");
            bookRepo.save(b4);

            Book b5 = new Book();
            b5.setTitle("The Midnight Hour");
            b5.setAuthor("James Adams");
            b5.setPublisher("Horror House");
            b5.setDescription("Haunting tale of a man's journey & the shadows of a forgotten past.");
            b5.setPrice(new BigDecimal("299.00"));
            b5.setFormat("Paperback");
            b5.setLanguage("English");
            b5.setRating(4.5);
            b5.setReviewCount(78);
            b5.setCopiesSold(210);
            b5.setIsBestseller(true);
            b5.setIsNewLaunch(false);
            b5.setImageUrl("/images/midnight-hour.png");
            b5.setCategory(c2);
            b5.setTentativeDeliveryDate("Mon, 21 Jul");
            bookRepo.save(b5);

            Book b6 = new Book();
            b6.setTitle("Beneath the Stars");
            b6.setAuthor("Jessica Martin");
            b6.setPublisher("Starlight Romance");
            b6.setDescription("A heartwarming romance where two souls discover what they need most.");
            b6.setPrice(new BigDecimal("499.00"));
            b6.setFormat("Hardcover");
            b6.setLanguage("English");
            b6.setRating(4.9);
            b6.setReviewCount(112);
            b6.setCopiesSold(340);
            b6.setIsBestseller(true);
            b6.setIsNewLaunch(false);
            b6.setImageUrl("/images/beneath-the-stars.png");
            b6.setCategory(c4);
            b6.setTentativeDeliveryDate("Mon, 21 Jul");
            bookRepo.save(b6);

            Book b7 = new Book();
            b7.setTitle("The Final Frontier");
            b7.setAuthor("Laura Mitchell");
            b7.setPublisher("Orion Sci-Fi");
            b7.setDescription("A mission to space secrets to change humanity forever.");
            b7.setPrice(new BigDecimal("359.00"));
            b7.setFormat("Paperback");
            b7.setLanguage("English");
            b7.setRating(4.7);
            b7.setReviewCount(67);
            b7.setCopiesSold(190);
            b7.setIsBestseller(true);
            b7.setIsNewLaunch(false);
            b7.setImageUrl("/images/final-frontier.png");
            b7.setCategory(c3);
            b7.setTentativeDeliveryDate("Mon, 21 Jul");
            bookRepo.save(b7);

            Book b8 = new Book();
            b8.setTitle("The Vanishing House");
            b8.setAuthor("Clara Nelson");
            b8.setPublisher("Mystery Works");
            b8.setDescription("A chilling mystery unfolds within a house that disappears.");
            b8.setPrice(new BigDecimal("99.00"));
            b8.setFormat("eBook");
            b8.setLanguage("English");
            b8.setRating(4.4);
            b8.setReviewCount(52);
            b8.setCopiesSold(400);
            b8.setIsBestseller(false);
            b8.setIsNewLaunch(true);
            b8.setImageUrl("/images/vanishing-house.png");
            b8.setCategory(c2);
            b8.setTentativeDeliveryDate("Mon, 21 Jul");
            bookRepo.save(b8);

            Book b9 = new Book();
            b9.setTitle("The Lost Kitten");
            b9.setAuthor("Emily Parker");
            b9.setPublisher("Kids Corner");
            b9.setDescription("A heartwarming tale of courage, friendship, and feline adventure.");
            b9.setPrice(new BigDecimal("339.00"));
            b9.setFormat("Hardcover");
            b9.setLanguage("English");
            b9.setRating(4.9);
            b9.setReviewCount(48);
            b9.setCopiesSold(150);
            b9.setIsBestseller(false);
            b9.setIsNewLaunch(true);
            b9.setImageUrl("/images/lost-kitten.png");
            b9.setCategory(c5);
            b9.setTentativeDeliveryDate("Mon, 21 Jul");
            bookRepo.save(b9);

            // 3. Seed User and Default Address
            User user = new User("Daniel Reed", "daniel@example.com", "password123", "+91 1234567890");
            user.setGiftPoints(250);
            user = userRepo.save(user);

            Address addr = new Address();
            addr.setFirstName("Daniel");
            addr.setLastName("Reed");
            addr.setAddressLine("Address Line 2, Tech Park Boulevard");
            addr.setCity("Mumbai");
            addr.setState("Maharashtra");
            addr.setPin("400001");
            addr.setCountry("India");
            addr.setPhone("+91 1234567890");
            addr.setEmail("daniel@example.com");
            addr.setIsDefault(true);
            addr.setUser(user);
            addressRepo.save(addr);

            // 4. Seed Sample Review
            reviewRepo.save(new Review("John Smith", 5, "The accordion component delivers large amounts of content in a small space through progressive disclosure. The user gets key details about the underlying content.", b1));
        };
    }
}
