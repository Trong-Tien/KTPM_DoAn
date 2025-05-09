package iuh.fit.restaurant_service.controller;

import iuh.fit.restaurant_service.dto.PromotionDTO;
import iuh.fit.restaurant_service.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant/{restaurantId}/promotion")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
public class PromotionUserController {

    private final PromotionService promotionService;

    // ✅ Lấy danh sách khuyến mãi của một nhà hàng cụ thể
    @GetMapping
    public ResponseEntity<List<PromotionDTO>> getPromotionsByRestaurant(@PathVariable String restaurantId) {
        List<PromotionDTO> promotions = promotionService.getPromotionsByRestaurant(restaurantId);
        return ResponseEntity.ok(promotions);
    }

    // ✅ Lấy tất cả khuyến mãi đang áp dụng cho một món ăn cụ thể
    @GetMapping("/menu/{menuItemId}")
    public ResponseEntity<List<PromotionDTO>> getPromotionsByMenuItem(@PathVariable String menuItemId) {
        List<PromotionDTO> promotions = promotionService.getPromotionsByMenuItem(menuItemId);
        return ResponseEntity.ok(promotions);
    }
}

