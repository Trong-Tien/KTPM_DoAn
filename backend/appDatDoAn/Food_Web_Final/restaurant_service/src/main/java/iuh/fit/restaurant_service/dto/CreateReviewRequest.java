package iuh.fit.restaurant_service.dto;

import jakarta.validation.constraints.*;

import lombok.Data;

@Data
public class CreateReviewRequest {
    @NotBlank
    private String menuItemId;

    @Min(1)
    @Max(5)
    private int rating;
    @NotBlank(message = "RestaurantId không được để trống")
    private String restaurantId;
    private String comment;
}

