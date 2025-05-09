package iuh.fit.restaurant_service.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReviewDTO {
    private String id;
    private String userId;
    private String menuItemId;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;
    private String adminReply;
    private String restaurantId;

}
