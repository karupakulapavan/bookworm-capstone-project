package com.bookworm.repository;

import com.bookworm.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT b FROM Book b WHERE " +
            "(:categoryId IS NULL OR b.category.id = :categoryId) AND " +
            "(:language IS NULL OR LOWER(b.language) = :language) AND " +
            "(:format IS NULL OR LOWER(b.format) = :format) AND " +
            "(:minPrice IS NULL OR b.price >= :minPrice) AND " +
            "(:maxPrice IS NULL OR b.price <= :maxPrice) AND " +
            "(:search IS NULL OR LOWER(b.title) LIKE :search OR LOWER(b.author) LIKE :search)")
    List<Book> searchAndFilter(
            @Param("categoryId") Long categoryId,
            @Param("language") String language,
            @Param("format") String format,
            @Param("minPrice") BigDecimal minPrice,
            @Param("maxPrice") BigDecimal maxPrice,
            @Param("search") String search);

    List<Book> findByIsBestsellerTrue();

    List<Book> findByIsNewLaunchTrue();

    List<Book> findByCategoryId(Long categoryId);

    @Query("SELECT b FROM Book b WHERE b.category.id = :categoryId AND b.id <> :bookId")
    List<Book> findRelatedBooks(@Param("categoryId") Long categoryId, @Param("bookId") Long bookId);
}
