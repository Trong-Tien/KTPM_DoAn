package iuh.fit.restaurant_service.service;

import iuh.fit.restaurant_service.dto.RestaurantDTO;
import iuh.fit.restaurant_service.exception.ResourceNotFoundException;
import iuh.fit.restaurant_service.mapper.RestaurantMapper;
import iuh.fit.restaurant_service.model.Restaurant;
import iuh.fit.restaurant_service.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    // Các phương thức quản lý nhà hàng (tạo, cập nhật, xóa, tìm kiếm)
    

    public Restaurant updateRestaurant(String id, Restaurant restaurant) {
        if (!restaurantRepository.existsById(id)) {
            throw new ResourceNotFoundException("Khong tìm thấy nhà hàng");
        }
        restaurant.setId(id);
        return restaurantRepository.save(restaurant);
    }

    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    public void deleteRestaurant(String id) {
        if (!restaurantRepository.existsById(id)) {
            throw new ResourceNotFoundException("Khong tìm thấy nhà hàng");
        }
        restaurantRepository.deleteById(id);
    }

    public Restaurant getRestaurantById(String id) {
        return restaurantRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Restaurant not found"));
    }

    public List<Restaurant> searchRestaurants(String keyword) {
        return restaurantRepository.findByNameContainingIgnoreCase(keyword);
    }

    public boolean existsById(String id) {
        return restaurantRepository.existsById(id);
    }

    public List<RestaurantDTO> findNearbyRestaurants(double lat, double lng, double radiusKm) {
        Point location = new Point(lng, lat); // Chú ý: (longitude, latitude)
        Distance distance = new Distance(radiusKm, Metrics.KILOMETERS);
        return restaurantRepository.findByLocationNear(location, distance)
                .stream()
                .map(RestaurantMapper::toDTO)
                .collect(Collectors.toList());
    }

    public Restaurant createRestaurant(RestaurantDTO dto, String managerId) {
        // ✅ Nếu DTO có id, kiểm tra xem đã tồn tại hay chưa
        if (dto.getId() != null && restaurantRepository.existsById(dto.getId())) {
            throw new IllegalArgumentException("Nhà hàng với ID '" + dto.getId() + "' đã tồn tại.");
        }

        Restaurant restaurant = RestaurantMapper.toEntity(dto);

        // ✅ Gán lại ID nếu có
        if (dto.getId() != null && !dto.getId().isBlank()) {
            restaurant.setId(dto.getId());
        }

        // ✅ Gán vị trí nếu có
        if (dto.getLatitude() != null && dto.getLongitude() != null) {
            restaurant.setLocation(new GeoJsonPoint(dto.getLongitude(), dto.getLatitude()));
        }

        // ✅ Gán người quản lý
        restaurant.setManagerId(managerId);

        return restaurantRepository.save(restaurant);
    }


    public List<Restaurant> getRestaurantsByManager(String managerId) {
        return restaurantRepository.findByManagerId(managerId);
    }




}
