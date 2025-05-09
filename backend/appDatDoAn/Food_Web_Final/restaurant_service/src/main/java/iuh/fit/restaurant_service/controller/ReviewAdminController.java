package iuh.fit.restaurant_service.controller;

import iuh.fit.restaurant_service.dto.ReplyReviewRequest;
import iuh.fit.restaurant_service.dto.ReviewDTO;
import iuh.fit.restaurant_service.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/restaurant/review")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class ReviewAdminController {

    private final ReviewService reviewService;

    @GetMapping
    public ResponseEntity<List<ReviewDTO>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @PutMapping("/{reviewId}/reply")
    public ResponseEntity<ReviewDTO> replyToReview(@PathVariable String reviewId,
                                                   @Valid @RequestBody ReplyReviewRequest request) {
        return ResponseEntity.ok(reviewService.replyToReview(reviewId, request.getReply()));
    }
    // ✅ Xem danh sách đánh giá theo nhà hàng
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<ReviewDTO>> getReviewsByRestaurant(@PathVariable String restaurantId) {
        return ResponseEntity.ok(reviewService.getReviewsByRestaurant(restaurantId));
    }


}

