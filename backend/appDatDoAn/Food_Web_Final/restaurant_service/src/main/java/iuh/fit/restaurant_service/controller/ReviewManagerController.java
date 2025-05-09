package iuh.fit.restaurant_service.controller;

import iuh.fit.restaurant_service.dto.ReplyReviewRequest;
import iuh.fit.restaurant_service.dto.ReviewDTO;
import iuh.fit.restaurant_service.model.Restaurant;
import iuh.fit.restaurant_service.security.JwtTokenUtil;
import iuh.fit.restaurant_service.service.MenuItemService;
import iuh.fit.restaurant_service.service.RestaurantService;
import iuh.fit.restaurant_service.service.ReviewService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manager/restaurant/{restaurantId}/review")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_MANAGER')")
public class ReviewManagerController {

    private final ReviewService reviewService;
    private final RestaurantService restaurantService;
    private final MenuItemService menuItemService;
    private final JwtTokenUtil jwtTokenUtil;

    // ✅ Xem tất cả review thuộc nhà hàng
    @GetMapping
    public ResponseEntity<?> getReviewsByRestaurant(@PathVariable String restaurantId,
                                                    HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String managerId = jwtTokenUtil.getUsername(token);

        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        if (!restaurant.getManagerId().equals(managerId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn không có quyền truy cập đánh giá nhà hàng này.");
        }

        List<ReviewDTO> reviews = reviewService.getReviewsByRestaurant(restaurantId);
        return ResponseEntity.ok(reviews);
    }

    // ✅ Trả lời review nếu thuộc nhà hàng quản lý
    @PutMapping("/{reviewId}/reply")
    public ResponseEntity<?> replyToReview(@PathVariable String restaurantId,
                                           @PathVariable String reviewId,
                                           @Valid @RequestBody ReplyReviewRequest request,
                                           HttpServletRequest httpRequest) {
        String token = httpRequest.getHeader("Authorization").substring(7);
        String managerId = jwtTokenUtil.getUsername(token);

        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        if (!restaurant.getManagerId().equals(managerId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn không có quyền trả lời đánh giá nhà hàng này.");
        }

        ReviewDTO replied = reviewService.replyToReview(reviewId, request.getReply());
        return ResponseEntity.ok(replied);
    }
}
