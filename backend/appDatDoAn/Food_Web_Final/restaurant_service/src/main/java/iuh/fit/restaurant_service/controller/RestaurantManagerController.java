package iuh.fit.restaurant_service.controller ;

import iuh.fit.restaurant_service.model.Restaurant;
import iuh.fit.restaurant_service.security.JwtTokenUtil;
import iuh.fit.restaurant_service.service.RestaurantService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manager/restaurant")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_MANAGER')")
public class RestaurantManagerController {

    private final RestaurantService restaurantService;
    private final JwtTokenUtil jwtTokenUtil;

    // ✅ Trả về danh sách nhà hàng mà manager này được phân quyền
    @GetMapping
    public ResponseEntity<List<Restaurant>> getMyRestaurants(HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String managerId = jwtTokenUtil.getUsername(token);

        List<Restaurant> restaurants = restaurantService.getRestaurantsByManager(managerId);
        return ResponseEntity.ok(restaurants);
    }
}
