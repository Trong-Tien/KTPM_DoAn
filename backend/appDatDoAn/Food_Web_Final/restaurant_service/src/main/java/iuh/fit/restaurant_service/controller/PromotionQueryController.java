package iuh.fit.restaurant_service.controller;

import iuh.fit.restaurant_service.dto.PromotionDTO;
import iuh.fit.restaurant_service.service.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restaurant/{restaurantId}/menu")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
public class PromotionQueryController {

    private final PromotionService promotionService;

    @GetMapping("/{menuItemId}/promotion")
    public ResponseEntity<PromotionDTO> getActivePromotion(
            @PathVariable String restaurantId,
            @PathVariable String menuItemId
    ) {
        PromotionDTO promotion = promotionService.getActivePromotion(restaurantId, menuItemId);
        return ResponseEntity.ok(promotion);
    }
}

