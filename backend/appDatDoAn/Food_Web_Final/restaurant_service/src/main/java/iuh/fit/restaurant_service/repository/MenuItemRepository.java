package iuh.fit.restaurant_service.repository;

import iuh.fit.restaurant_service.model.MenuItem;
import iuh.fit.restaurant_service.model.Restaurant;
import iuh.fit.restaurant_service.repository.custom.MenuItemRepositoryCustom;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface MenuItemRepository extends MongoRepository<MenuItem, String>, MenuItemRepositoryCustom {
    List<MenuItem> findByRestaurantId(String restaurantId);
    List<MenuItem> findByNameContainingIgnoreCase(String name);
    List<MenuItem> findByRestaurantIdAndCategoryId(String restaurantId, String categoryId);
    Optional<MenuItem> findByIdAndRestaurantId(String id, String restaurantId);

    // Các phương thức tìm kiếm món ăn có thể được thêm vào đây
}
