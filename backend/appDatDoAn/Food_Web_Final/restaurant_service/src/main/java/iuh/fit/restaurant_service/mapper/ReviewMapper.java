package iuh.fit.restaurant_service.mapper;

import iuh.fit.restaurant_service.dto.ReviewDTO;
import iuh.fit.restaurant_service.model.Review;

public class ReviewMapper {
    public static ReviewDTO toDTO(Review review) {
        ReviewDTO dto = new ReviewDTO();
        dto.setId(review.getId());
        dto.setUserId(review.getUserId());
        dto.setMenuItemId(review.getMenuItemId());
        dto.setRating(review.getRating());
        dto.setComment(review.getComment());
        dto.setCreatedAt(review.getCreatedAt());
        dto.setAdminReply(review.getAdminReply());
        dto.setRestaurantId(review.getRestaurantId());
        return dto;
    }
}

