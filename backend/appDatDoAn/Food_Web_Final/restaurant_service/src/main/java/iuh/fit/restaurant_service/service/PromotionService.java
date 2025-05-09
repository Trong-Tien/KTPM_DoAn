package iuh.fit.restaurant_service.service;

import iuh.fit.restaurant_service.dto.PromotionDTO;
import iuh.fit.restaurant_service.exception.ResourceNotFoundException;
import iuh.fit.restaurant_service.mapper.PromotionMapper;
import iuh.fit.restaurant_service.model.MenuItem;
import iuh.fit.restaurant_service.model.Promotion;
import iuh.fit.restaurant_service.repository.MenuItemRepository;
import iuh.fit.restaurant_service.repository.PromotionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PromotionService {

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;

    public PromotionDTO createPromotion(String restaurantId, PromotionDTO dto) {
        // Kiểm tra xem menuItem có tồn tại và thuộc về nhà hàng không
        MenuItem menuItem = menuItemRepository.findById(dto.getMenuItemId())
                .filter(item -> restaurantId.equals(item.getRestaurantId()))
                .orElseThrow(() -> new ResourceNotFoundException("Món ăn không tồn tại hoặc không thuộc nhà hàng"));

        Promotion promotion = PromotionMapper.toEntity(dto);
        promotion.setRestaurantId(restaurantId);
        Promotion saved = promotionRepository.save(promotion);
        return PromotionMapper.toDTO(saved);
    }

    public List<PromotionDTO> getPromotionsByRestaurant(String restaurantId) {
        List<Promotion> promotions = promotionRepository.findByRestaurantId(restaurantId);
        return promotions.stream()
                .map(PromotionMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<PromotionDTO> getPromotionsByMenuItem(String menuItemId) {
        List<Promotion> promotions = promotionRepository.findByMenuItemId(menuItemId);
        return promotions.stream()
                .map(PromotionMapper::toDTO)
                .collect(Collectors.toList());
    }

    public PromotionDTO getActivePromotion(String restaurantId, String menuItemId) {
        LocalDate today = LocalDate.now(ZoneId.of("Asia/Ho_Chi_Minh"));
        return promotionRepository.findByRestaurantIdAndMenuItemId(restaurantId, menuItemId).stream()
                .filter(p -> !today.isBefore(p.getStartDate()) && !today.isAfter(p.getEndDate()))
                .findFirst()
                .map(PromotionMapper::toDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Không có khuyến mãi đang hoạt động cho món ăn này"));
    }

    public void deletePromotion(String id) {
        if (!promotionRepository.existsById(id)) {
            throw new ResourceNotFoundException("Không tìm thấy khuyến mãi");
        }
        promotionRepository.deleteById(id);
    }
}
