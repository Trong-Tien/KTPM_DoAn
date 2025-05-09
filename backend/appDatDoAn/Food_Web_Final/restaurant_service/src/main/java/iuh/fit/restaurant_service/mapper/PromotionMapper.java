package iuh.fit.restaurant_service.mapper;

import iuh.fit.restaurant_service.dto.PromotionDTO;
import iuh.fit.restaurant_service.model.Promotion;

public class PromotionMapper {

    public static PromotionDTO toDTO(Promotion promotion) {
        return new PromotionDTO(
                promotion.getId(),
                promotion.getMenuItemId(),
                promotion.getDiscountPercent(),
                promotion.getStartDate(),
                promotion.getEndDate()
        );
    }

    public static Promotion toEntity(PromotionDTO dto) {
        Promotion promotion = new Promotion();
        promotion.setId(dto.getId());
        promotion.setMenuItemId(dto.getMenuItemId());
        promotion.setDiscountPercent(dto.getDiscountPercent());
        promotion.setStartDate(dto.getStartDate());
        promotion.setEndDate(dto.getEndDate());
        return promotion;
    }
}
