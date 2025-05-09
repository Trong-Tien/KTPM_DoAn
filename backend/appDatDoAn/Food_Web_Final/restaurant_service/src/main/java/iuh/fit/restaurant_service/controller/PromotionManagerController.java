package iuh.fit.restaurant_service.controller;

import iuh.fit.restaurant_service.dto.PromotionDTO;
import iuh.fit.restaurant_service.model.Restaurant;
import iuh.fit.restaurant_service.security.JwtTokenUtil;
import iuh.fit.restaurant_service.service.PromotionService;
import iuh.fit.restaurant_service.service.RestaurantService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/manager/restaurant/{restaurantId}/promotion")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_MANAGER')")
public class PromotionManagerController {

    private final PromotionService promotionService;
    private final RestaurantService restaurantService;
    private final JwtTokenUtil jwtTokenUtil;

    // ✅ Tạo khuyến mãi mới
    @PostMapping
    public ResponseEntity<?> createPromotion(
            @PathVariable String restaurantId,
            @Valid @RequestBody PromotionDTO dto,
            HttpServletRequest request
    ) {
        String token = request.getHeader("Authorization").substring(7);
        String managerId = jwtTokenUtil.getUsername(token);

        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        if (!restaurant.getManagerId().equals(managerId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn không có quyền tạo khuyến mãi cho nhà hàng này.");
        }

        PromotionDTO created = promotionService.createPromotion(restaurantId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // ✅ Xoá khuyến mãi
    @DeleteMapping("/{promotionId}")
    public ResponseEntity<?> deletePromotion(
            @PathVariable String restaurantId,
            @PathVariable String promotionId,
            HttpServletRequest request
    ) {
        String token = request.getHeader("Authorization").substring(7);
        String managerId = jwtTokenUtil.getUsername(token);

        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        if (!restaurant.getManagerId().equals(managerId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn không có quyền xóa khuyến mãi của nhà hàng này.");
        }

        promotionService.deletePromotion(promotionId);
        return ResponseEntity.ok("Đã xoá khuyến mãi.");
    }
}
