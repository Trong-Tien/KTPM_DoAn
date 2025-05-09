package iuh.fit.restaurant_service.mapper;

import iuh.fit.restaurant_service.dto.MenuItemDTO;
import iuh.fit.restaurant_service.model.MenuItem;
import iuh.fit.restaurant_service.dto.PromotionDTO;

public class MenuItemMapper {

    public static MenuItemDTO toDTO(MenuItem menuItem) {
        MenuItemDTO dto = new MenuItemDTO();
        dto.setId(menuItem.getId());
        dto.setName(menuItem.getName());
        dto.setDescription(menuItem.getDescription());
        dto.setPrice(menuItem.getPrice());
        dto.setImageUrl(menuItem.getImageUrl());
        dto.setRestaurantId(menuItem.getRestaurantId());
        dto.setCategoryId(menuItem.getCategoryId());
        dto.setStock(menuItem.getStock());
        return dto;
    }

    public static MenuItemDTO toDTO(MenuItem menuItem, PromotionDTO promotion) {
        MenuItemDTO dto = new MenuItemDTO();
        dto.setId(menuItem.getId());
        dto.setName(menuItem.getName());
        dto.setDescription(menuItem.getDescription());
        dto.setPrice(menuItem.getPrice());
        dto.setImageUrl(menuItem.getImageUrl());
        dto.setRestaurantId(menuItem.getRestaurantId());
        dto.setCategoryId(menuItem.getCategoryId());
        dto.setStock(menuItem.getStock());
        dto.setPromotion(promotion); // ✅ gán từ tham số
        return dto;
    }


    public static MenuItem toEntity(MenuItemDTO dto) {
        MenuItem menuItem = new MenuItem();
        menuItem.setId(dto.getId());
        menuItem.setName(dto.getName());
        menuItem.setDescription(dto.getDescription());
        menuItem.setPrice(dto.getPrice());
        menuItem.setImageUrl(dto.getImageUrl());
        menuItem.setRestaurantId(dto.getRestaurantId());
        menuItem.setCategoryId(dto.getCategoryId());
        menuItem.setStock(dto.getStock());
        return menuItem;
    }
}
