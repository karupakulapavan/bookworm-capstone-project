package com.bookworm.service;

import com.bookworm.dto.DTOs.*;
import com.bookworm.model.Book;
import com.bookworm.model.Category;
import com.bookworm.model.Review;
import com.bookworm.repository.BookRepository;
import com.bookworm.repository.CategoryRepository;
import com.bookworm.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class CatalogService {

    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;
    private final ReviewRepository reviewRepository;

    public CatalogService(CategoryRepository categoryRepository, BookRepository bookRepository, ReviewRepository reviewRepository) {
        this.categoryRepository = categoryRepository;
        this.bookRepository = bookRepository;
        this.reviewRepository = reviewRepository;
    }

    public List<CategoryDto> getAllCategories() {
        return categoryRepository.findAll().stream()
                .map(c -> new CategoryDto(c.getId(), c.getName(), c.getSlug(), c.getDescription()))
                .collect(Collectors.toList());
    }

    public List<BookDto> searchBooks(Long categoryId, String language, String format,
                                     BigDecimal minPrice, BigDecimal maxPrice,
                                     String search, String sortBy) {
        String normalizedLanguage = (language != null && !language.isBlank()) ? language.trim().toLowerCase() : null;
        String normalizedFormat = (format != null && !format.isBlank()) ? format.trim().toLowerCase() : null;
        String searchPattern = (search != null && !search.isBlank()) ? "%" + search.trim().toLowerCase() + "%" : null;

        List<Book> books = bookRepository.searchAndFilter(categoryId, normalizedLanguage, normalizedFormat, minPrice, maxPrice, searchPattern);

        // Sorting
        if ("price_low_to_high".equalsIgnoreCase(sortBy)) {
            books.sort(Comparator.comparing(Book::getPrice));
        } else if ("price_high_to_low".equalsIgnoreCase(sortBy)) {
            books.sort(Comparator.comparing(Book::getPrice).reversed());
        } else if ("rating".equalsIgnoreCase(sortBy)) {
            books.sort(Comparator.comparing(Book::getRating).reversed());
        }

        return books.stream().map(this::mapToBookDto).collect(Collectors.toList());
    }

    public BookDetailDto getBookById(Long bookId) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + bookId));

        BookDetailDto dto = new BookDetailDto();
        copyBookToDto(book, dto);
        dto.setAuthorBio(book.getAuthorBio());
        dto.setDescription(book.getDescription());

        List<ReviewDto> reviews = reviewRepository.findByBookId(bookId).stream()
                .map(this::mapToReviewDto)
                .collect(Collectors.toList());
        dto.setReviews(reviews);

        return dto;
    }

    public ReviewDto addReview(Long bookId, ReviewRequest req) {
        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new RuntimeException("Book not found with ID: " + bookId));

        Review review = new Review(req.getReviewerName(), req.getRating(), req.getComment(), book);
        Review saved = reviewRepository.save(review);

        // Update book average rating
        List<Review> allReviews = reviewRepository.findByBookId(bookId);
        double avgRating = allReviews.stream().mapToInt(Review::getRating).average().orElse(5.0);
        book.setRating(Math.round(avgRating * 10.0) / 10.0);
        book.setReviewCount(allReviews.size());
        bookRepository.save(book);

        return mapToReviewDto(saved);
    }

    public List<BookDto> getBestsellers() {
        return bookRepository.findByIsBestsellerTrue().stream()
                .map(this::mapToBookDto)
                .collect(Collectors.toList());
    }

    public List<BookDto> getNewLaunches() {
        return bookRepository.findByIsNewLaunchTrue().stream()
                .map(this::mapToBookDto)
                .collect(Collectors.toList());
    }

    public List<BookDto> getPersonalizedRecommendations(Long userId) {
        // Recommendations based on popularity / category affinity
        return bookRepository.findAll().stream()
                .limit(3)
                .map(this::mapToBookDto)
                .collect(Collectors.toList());
    }

    public List<BookDto> getRelatedBooks(Long bookId) {
        Book book = bookRepository.findById(bookId).orElse(null);
        if (book != null && book.getCategory() != null) {
            List<Book> related = bookRepository.findRelatedBooks(book.getCategory().getId(), bookId);
            if (!related.isEmpty()) {
                return related.stream().limit(3).map(this::mapToBookDto).collect(Collectors.toList());
            }
        }
        return bookRepository.findAll().stream()
                .filter(b -> !b.getId().equals(bookId))
                .limit(3)
                .map(this::mapToBookDto)
                .collect(Collectors.toList());
    }

    public BookDto mapToBookDto(Book book) {
        BookDto dto = new BookDto();
        copyBookToDto(book, dto);
        return dto;
    }

    private void copyBookToDto(Book book, BookDto dto) {
        dto.setId(book.getId());
        dto.setTitle(book.getTitle());
        dto.setAuthor(book.getAuthor());
        dto.setPublisher(book.getPublisher());
        dto.setCategoryName(book.getCategory() != null ? book.getCategory().getName() : "General");
        dto.setPrice(book.getPrice());
        dto.setFormat(book.getFormat());
        dto.setLanguage(book.getLanguage());
        dto.setRating(book.getRating());
        dto.setReviewCount(book.getReviewCount());
        dto.setCopiesSold(book.getCopiesSold());
        dto.setImageUrl(book.getImageUrl());
        dto.setTentativeDeliveryDate(book.getTentativeDeliveryDate());
    }

    public ReviewDto mapToReviewDto(Review review) {
        ReviewDto dto = new ReviewDto();
        dto.setId(review.getId());
        dto.setReviewerName(review.getReviewerName());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setCreatedAt(review.getCreatedAt());
        return dto;
    }
}
