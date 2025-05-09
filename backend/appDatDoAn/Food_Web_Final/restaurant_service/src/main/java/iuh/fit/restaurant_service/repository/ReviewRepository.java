package iuh.fit.restaurant_service.repository;

import iuh.fit.restaurant_service.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ReviewRepository extends MongoRepository<Review, String> {
    List<Review> findByMenuItemId(String menuItemId);
    boolean existsByUserIdAndMenuItemId(String userId, String menuItemId);
    List<Review> findByMenuItemIdIn(List<String> menuItemIds);
}

