package iuh.fit.restaurant_service.controller;

import iuh.fit.restaurant_service.dto.CreateReviewRequest;
import iuh.fit.restaurant_service.dto.ReviewDTO;
import iuh.fit.restaurant_service.security.JwtTokenUtil;
import iuh.fit.restaurant_service.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant/review")
@RequiredArgsConstructor
public class ReviewUserController {

    private final ReviewService reviewService;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public ResponseEntity<ReviewDTO> createReview(@RequestHeader("Authorization") String token,
                                                  @Valid @RequestBody CreateReviewRequest request) {
        String userId = jwtTokenUtil.getUsername(token.substring(7));
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(reviewService.createReview(userId, request));
    }

    @GetMapping("/menu/{menuItemId}")
    public ResponseEntity<List<ReviewDTO>> getReviews(@PathVariable String menuItemId) {
        return ResponseEntity.ok(reviewService.getReviewsByMenuItem(menuItemId));
    }

    @GetMapping("/restaurant/{restaurantId}/rating")
    public ResponseEntity<Double> getAverageRatingByRestaurant(@PathVariable String restaurantId) {
        return ResponseEntity.ok(reviewService.calculateAverageRatingByRestaurant(restaurantId));
    }


}
