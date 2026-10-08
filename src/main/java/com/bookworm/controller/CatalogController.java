package com.bookworm.controller;

import com.bookworm.dto.DTOs.*;
import com.bookworm.service.CatalogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "*")
@Tag(name = "Catalog & Recommendations", description = "Endpoints for Categories, Books Catalog, Filtering, Reviews and Recommendation Engine")
public class CatalogController {

    private final CatalogService catalogService;

    public CatalogController(CatalogService catalogService) {
        this.catalogService = catalogService;
    }

    @GetMapping("/catalog/categories")
    @Operation(summary = "Get all categories")
    public ResponseEntity<List<CategoryDto>> getCategories() {
        return ResponseEntity.ok(catalogService.getAllCategories());
    }

    @GetMapping("/catalog/books")
    @Operation(summary = "Search, filter and sort book catalog")
    public ResponseEntity<List<BookDto>> getBooks(
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String language,
            @RequestParam(required = false) String format,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(required = false) String search,
            @RequestParam(required = false, defaultValue = "relevance") String sortBy
    ) {
        return ResponseEntity.ok(catalogService.searchBooks(categoryId, language, format, minPrice, maxPrice, search, sortBy));
    }

    @GetMapping("/catalog/books/{bookId}")
    @Operation(summary = "Get detailed book information and user reviews")
    public ResponseEntity<BookDetailDto> getBookById(@PathVariable Long bookId) {
        return ResponseEntity.ok(catalogService.getBookById(bookId));
    }

    @PostMapping("/catalog/books/{bookId}/reviews")
    @Operation(summary = "Submit a review for a book")
    public ResponseEntity<ReviewDto> addReview(@PathVariable Long bookId, @RequestBody ReviewRequest req) {
        return new ResponseEntity<>(catalogService.addReview(bookId, req), HttpStatus.CREATED);
    }

    @GetMapping("/recommendations/personalized")
    @Operation(summary = "Get personalised recommendations based on user order history")
    public ResponseEntity<List<BookDto>> getPersonalizedRecommendations(@RequestParam Long userId) {
        return ResponseEntity.ok(catalogService.getPersonalizedRecommendations(userId));
    }

    @GetMapping("/recommendations/bestsellers")
    @Operation(summary = "Get bestsellers of the month")
    public ResponseEntity<List<BookDto>> getBestsellers() {
        return ResponseEntity.ok(catalogService.getBestsellers());
    }

    @GetMapping("/recommendations/new-launches")
    @Operation(summary = "Get newly launched books")
    public ResponseEntity<List<BookDto>> getNewLaunches() {
        return ResponseEntity.ok(catalogService.getNewLaunches());
    }

    @GetMapping("/recommendations/related/{bookId}")
    @Operation(summary = "Get related / cross-sell and up-sell books")
    public ResponseEntity<List<BookDto>> getRelatedBooks(@PathVariable Long bookId) {
        return ResponseEntity.ok(catalogService.getRelatedBooks(bookId));
    }
}
