package iuh.fit.restaurant_service.repository;

import iuh.fit.restaurant_service.model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends MongoRepository<Category, String> {
    List<Category> findByRestaurantId(String restaurantId);
    Optional<Category> findByRestaurantIdAndId(String restaurantId, String categoryId);
    // Các phương thức tìm kiếm loại món ăn có thể được thêm vào đây
}
