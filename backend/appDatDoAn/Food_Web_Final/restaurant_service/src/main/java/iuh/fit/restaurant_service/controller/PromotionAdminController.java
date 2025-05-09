package iuh.fit.restaurant_service.controller;

import iuh.fit.restaurant_service.dto.PromotionDTO;
import iuh.fit.restaurant_service.service.PromotionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/restaurant/{restaurantId}/promotion")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class PromotionAdminController {

    private final PromotionService promotionService;

    // ✅ Tạo chương trình khuyến mãi mới cho món ăn thuộc nhà hàng
    @PostMapping
    public ResponseEntity<PromotionDTO> createPromotion(
            @PathVariable String restaurantId,
            @Valid @RequestBody PromotionDTO dto) {
        PromotionDTO created = promotionService.createPromotion(restaurantId, dto);
        return ResponseEntity.ok(created);
    }

    // ✅ Xóa khuyến mãi theo ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePromotion(@PathVariable String id) {
        promotionService.deletePromotion(id);
        return ResponseEntity.noContent().build();
    }

}

