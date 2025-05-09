package iuh.fit.restaurant_service.controller ;

import iuh.fit.restaurant_service.dto.MenuItemDTO;
import iuh.fit.restaurant_service.model.Restaurant;
import iuh.fit.restaurant_service.security.JwtTokenUtil;
import iuh.fit.restaurant_service.service.MenuItemService;
import iuh.fit.restaurant_service.service.RestaurantService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/manager/restaurant")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_MANAGER')")
public class MenuItemManagerController {

    private final MenuItemService menuItemService;
    private final RestaurantService restaurantService;
    private final JwtTokenUtil jwtTokenUtil;

    // ✅ Tạo món ăn
    @PostMapping("/{restaurantId}/menu")
    public ResponseEntity<?> createMenuItem(@PathVariable String restaurantId,
                                            @Valid @RequestBody MenuItemDTO dto,
                                            HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String managerId = jwtTokenUtil.getUsername(token);

        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        if (!restaurant.getManagerId().equals(managerId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn không có quyền truy cập nhà hàng này.");
        }

        MenuItemDTO created = menuItemService.createMenuItem(restaurantId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // ✅ Cập nhật món ăn
    @PutMapping("/{restaurantId}/menu/{menuId}")
    public ResponseEntity<?> updateMenuItem(@PathVariable String restaurantId,
                                            @PathVariable String menuId,
                                            @Valid @RequestBody MenuItemDTO dto,
                                            HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String managerId = jwtTokenUtil.getUsername(token);

        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        if (!restaurant.getManagerId().equals(managerId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn không có quyền cập nhật nhà hàng này.");
        }

        MenuItemDTO updated = menuItemService.updateMenuItem(restaurantId, menuId, dto);
        return ResponseEntity.ok(updated);
    }

    // ✅ Xoá món ăn
    @DeleteMapping("/{restaurantId}/menu/{menuId}")
    public ResponseEntity<?> deleteMenuItem(@PathVariable String restaurantId,
                                            @PathVariable String menuId,
                                            HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String managerId = jwtTokenUtil.getUsername(token);

        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        if (!restaurant.getManagerId().equals(managerId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn không có quyền xoá món ăn này.");
        }

        menuItemService.deleteMenuItem(restaurantId, menuId);
        return ResponseEntity.ok("Đã xoá món ăn.");
    }
}
