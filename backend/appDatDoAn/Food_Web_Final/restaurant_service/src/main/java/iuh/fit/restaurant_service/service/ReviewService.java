package iuh.fit.restaurant_service.service;

import iuh.fit.restaurant_service.client.OrderClient;
import iuh.fit.restaurant_service.dto.CreateReviewRequest;
import iuh.fit.restaurant_service.dto.ReviewDTO;
import iuh.fit.restaurant_service.exception.ResourceNotFoundException;
import iuh.fit.restaurant_service.mapper.ReviewMapper;
import iuh.fit.restaurant_service.model.MenuItem;
import iuh.fit.restaurant_service.model.Review;
import iuh.fit.restaurant_service.repository.MenuItemRepository;
import iuh.fit.restaurant_service.repository.RestaurantRepository;
import iuh.fit.restaurant_service.repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final OrderClient orderClient; // Feign Client đến order_service
    private final RestaurantRepository restaurantRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    public ReviewDTO createReview(String userId, CreateReviewRequest request) {
        // ✅ Kiểm tra restaurantId hợp lệ
        if (!restaurantRepository.existsById(request.getRestaurantId())) {
            throw new ResourceNotFoundException("Nhà hàng không tồn tại");
        }

        // ✅ Kiểm tra quyền đánh giá từ order_service
        boolean allowed = orderClient.hasUserOrderedAndReceived(userId, request.getMenuItemId());
        if (!allowed) {
            throw new AccessDeniedException("Bạn chưa đủ điều kiện đánh giá món ăn này");
        }

        // ✅ Tạo review
        Review review = new Review();
        review.setUserId(userId);
        review.setMenuItemId(request.getMenuItemId());
        review.setRestaurantId(request.getRestaurantId());
        review.setRating(request.getRating());
        review.setComment(request.getComment());
        review.setCreatedAt(LocalDateTime.now());

        return ReviewMapper.toDTO(reviewRepository.save(review));
    }



    public List<ReviewDTO> getReviewsByMenuItem(String menuItemId) {
        return reviewRepository.findByMenuItemId(menuItemId)
                .stream()
                .map(ReviewMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ReviewDTO> getReviewsByRestaurant(String restaurantId) {
        // Lấy tất cả review của các món ăn thuộc nhà hàng đó
        List<MenuItem> menuItems = menuItemRepository.findByRestaurantId(restaurantId);
        List<String> menuItemIds = menuItems.stream()
                .map(MenuItem::getId)
                .toList();

        List<Review> reviews = reviewRepository.findByMenuItemIdIn(menuItemIds);
        return reviews.stream()
                .map(ReviewMapper::toDTO)
                .toList();
    }


    public ReviewDTO replyToReview(String reviewId, String reply) {
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đánh giá"));

        review.setAdminReply(reply);
        review.setUpdatedAt(LocalDateTime.now());
        reviewRepository.save(review);

        return ReviewMapper.toDTO(review);
    }

    public List<ReviewDTO> getAllReviews() {
        return reviewRepository.findAll()
                .stream()
                .map(ReviewMapper::toDTO)
                .collect(Collectors.toList());
    }

    public double calculateAverageRatingByRestaurant(String restaurantId) {
        List<String> menuItemIds = menuItemRepository.findByRestaurantId(restaurantId)
                .stream()
                .map(item -> item.getId())
                .toList();

        if (menuItemIds.isEmpty()) return 0.0;

        List<Review> reviews = reviewRepository.findByMenuItemIdIn(menuItemIds);
        if (reviews.isEmpty()) return 0.0;

        double average = reviews.stream().mapToInt(Review::getRating).average().orElse(0.0);
        return Math.round(average * 10.0) / 10.0; // làm tròn 1 chữ số
    }

}
