package iuh.fit.restaurant_service.repository;

import iuh.fit.restaurant_service.model.Restaurant;
import org.springframework.data.geo.Distance;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface RestaurantRepository extends MongoRepository<Restaurant, String> {
    // Các phương thức tìm kiếm nhà hàng có thể được thêm vào đây

    List<Restaurant> findByNameContainingIgnoreCase(String name);

    List<Restaurant> findByLocationNear(org.springframework.data.geo.Point location, Distance distance);
    List<Restaurant> findByManagerId(String managerId);

    




}
