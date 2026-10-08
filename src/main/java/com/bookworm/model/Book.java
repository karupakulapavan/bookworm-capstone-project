package com.bookworm.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String author;
    private String authorBio;
    private String publisher;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    private String format; // Paperback, Hardcover, eBook
    private String language = "English";

    private Double rating = 4.5;
    private Integer reviewCount = 0;
    private Integer copiesSold = 0;

    private String imageUrl;
    private Boolean isBestseller = false;
    private Boolean isNewLaunch = false;

    private String tentativeDeliveryDate = "Mon, 21 Jul";

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews = new ArrayList<>();

    public Book() {}

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getAuthorBio() { return authorBio; }
    public void setAuthorBio(String authorBio) { this.authorBio = authorBio; }

    public String getPublisher() { return publisher; }
    public void setPublisher(String publisher) { this.publisher = publisher; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

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

    public Boolean getIsBestseller() { return isBestseller; }
    public void setIsBestseller(Boolean isBestseller) { this.isBestseller = isBestseller; }

    public Boolean getIsNewLaunch() { return isNewLaunch; }
    public void setIsNewLaunch(Boolean isNewLaunch) { this.isNewLaunch = isNewLaunch; }

    public String getTentativeDeliveryDate() { return tentativeDeliveryDate; }
    public void setTentativeDeliveryDate(String tentativeDeliveryDate) { this.tentativeDeliveryDate = tentativeDeliveryDate; }

    public Category getCategory() { return category; }
    public void setCategory(Category category) { this.category = category; }

    public List<Review> getReviews() { return reviews; }
    public void setReviews(List<Review> reviews) { this.reviews = reviews; }
}
