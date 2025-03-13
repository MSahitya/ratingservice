package com.tastebytes.ratingservice.entity;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "user_rating")
@Data
@Schema(description = "Model representing a user's rating.")

public class UserRating {
    @Schema(description = "Unique identifier for the rating", example = "1")
    private String id;

    @NotEmpty(message = "User ID cannot be empty")
    @Pattern(regexp = "^[0-9]{5,10}$", message = "User ID must be alphanumeric and between 5 to 10 characters")
    @Schema(description = "Unique identifier for the user", example = "12345")
    private String userId;

    @Schema(description = "Unique identifier for the product", example = "708098098djfjdsifjsdf")
    private String productId;

    @Schema(description = "Rating value", example = "4")
    private int rating;

    public UserRating(String id, String userId, String productId, int rating) {
        this.id = id;
        this.userId = userId;
        this.productId = productId;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRating that = (UserRating) o;
        return rating == that.rating && Objects.equals(id, that.id) && Objects.equals(userId, that.userId) && Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, productId, rating);
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getUserId() {
        return userId;
    }

    public UserRating() {
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setId(String id) {
        this.id = id;
    }
}
