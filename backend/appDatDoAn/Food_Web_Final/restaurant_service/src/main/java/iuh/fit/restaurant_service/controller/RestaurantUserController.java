package iuh.fit.restaurant_service.controller;

import iuh.fit.restaurant_service.dto.RestaurantDTO;
import iuh.fit.restaurant_service.mapper.RestaurantMapper;
import iuh.fit.restaurant_service.model.Restaurant;
import iuh.fit.restaurant_service.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
public class RestaurantUserController {

    private final RestaurantService restaurantService;

    // ✅ Kiểm tra sự tồn tại nhà hàng theo ID
    @GetMapping("/{id}/exists")
    public ResponseEntity<Boolean> checkRestaurantExists(@PathVariable String id) {
        boolean exists = restaurantService.existsById(id);
        return ResponseEntity.ok(exists);
    }

    // ✅ Xem chi tiết nhà hàng
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantDTO> getRestaurantById(@PathVariable String id) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        return ResponseEntity.ok(RestaurantMapper.toDTO(restaurant));
    }


    // ✅ Lấy tất cả nhà hàng
    @GetMapping
    public ResponseEntity<List<Restaurant>> getAllRestaurants() {
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        return ResponseEntity.ok(restaurants);
    }

    // ✅ Tìm kiếm nhà hàng theo từ khóa
    @GetMapping("/search")
    public ResponseEntity<List<Restaurant>> searchRestaurants(@RequestParam String keyword) {
        List<Restaurant> restaurants = restaurantService.searchRestaurants(keyword);
        return ResponseEntity.ok(restaurants);
    }

    @GetMapping("/nearby")
    public ResponseEntity<List<RestaurantDTO>> getNearbyRestaurants(
            @RequestParam double lat,
            @RequestParam double lng,
            @RequestParam(defaultValue = "3") double radiusKm
    ) {
        return ResponseEntity.ok(restaurantService.findNearbyRestaurants(lat, lng, radiusKm));
    }

}
