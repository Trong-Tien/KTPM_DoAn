package iuh.fit.restaurant_service.controller;

import iuh.fit.restaurant_service.dto.RestaurantDTO;
import iuh.fit.restaurant_service.model.Restaurant;
import iuh.fit.restaurant_service.repository.MenuItemRepository;
import iuh.fit.restaurant_service.repository.RestaurantRepository;
import iuh.fit.restaurant_service.service.RestaurantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/restaurant")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class RestaurantAdminController {

    private final RestaurantService restaurantService;
    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;

    // ✅ Tạo nhà hàng mới
    @PostMapping
    public ResponseEntity<Restaurant> createRestaurant(@Valid @RequestBody RestaurantDTO dto) {
        if (dto.getManagerId() == null || dto.getManagerId().isBlank()) {
            return ResponseEntity.badRequest().body(null); // hoặc custom message
        }

        Restaurant created = restaurantService.createRestaurant(dto, dto.getManagerId());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/count")
    public ResponseEntity<Long> countRestaurants() {
        return ResponseEntity.ok(restaurantRepository.count());
    }

    @GetMapping("/menu-item/count")
    public ResponseEntity<Long> countMenuItems() {
        return ResponseEntity.ok(menuItemRepository.count());
    }



    // ✅ Cập nhật nhà hàng
    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable String id,
                                                       @Valid @RequestBody RestaurantDTO dto) {
        // Giữ lại managerId gốc nếu không muốn cho admin đổi người quản lý
        Restaurant existing = restaurantService.getRestaurantById(id);
        dto.setManagerId(existing.getManagerId());

        Restaurant updated = restaurantService.createRestaurant(dto, dto.getManagerId());
        return ResponseEntity.ok(updated);
    }


    // ✅ Xoá nhà hàng
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRestaurant(@PathVariable String id) {
        restaurantService.deleteRestaurant(id);
        return ResponseEntity.noContent().build();
    }
}
