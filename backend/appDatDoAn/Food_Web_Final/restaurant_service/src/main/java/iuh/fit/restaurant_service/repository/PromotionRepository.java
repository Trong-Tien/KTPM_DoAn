package iuh.fit.restaurant_service.repository;

import iuh.fit.restaurant_service.model.Promotion;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface PromotionRepository extends MongoRepository<Promotion, String> {

    List<Promotion>findByRestaurantId(String restaurantId);
    List<Promotion>findByMenuItemId(String menuId);
    List<Promotion> findByRestaurantIdAndMenuItemId(String restaurantId, String menuItemId);
    // Các phương thức tìm kiếm khuyến mãi có thể được thêm vào đây
    boolean existsByMenuItemId(String menuItemId);

}
