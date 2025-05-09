package iuh.fit.restaurant_service.service;

import iuh.fit.restaurant_service.client.OrderClient;
import iuh.fit.restaurant_service.dto.MenuItemDTO;
import iuh.fit.restaurant_service.dto.PromotionDTO;
import iuh.fit.restaurant_service.dto.TopSoldItemDTO;
import iuh.fit.restaurant_service.exception.ResourceNotFoundException;
import iuh.fit.restaurant_service.mapper.MenuItemMapper;
import iuh.fit.restaurant_service.mapper.PromotionMapper;
import iuh.fit.restaurant_service.model.MenuItem;
import iuh.fit.restaurant_service.model.Promotion;
import iuh.fit.restaurant_service.model.Restaurant;
import iuh.fit.restaurant_service.repository.CategoryRepository;
import iuh.fit.restaurant_service.repository.MenuItemRepository;
import iuh.fit.restaurant_service.repository.PromotionRepository;
import iuh.fit.restaurant_service.repository.RestaurantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static iuh.fit.restaurant_service.util.PromotionUtils.isPromotionActive;

@Service
public class MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private PromotionRepository promotionRepository;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private OrderClient orderClient;

    public MenuItemDTO createMenuItem(String restaurantId, MenuItemDTO dto) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhà hàng"));

        if (!categoryRepository.existsById(dto.getCategoryId())) {
            throw new ResourceNotFoundException("Không tìm thấy loại món ăn");
        }

        MenuItem item = MenuItemMapper.toEntity(dto);
        item.setRestaurantId(restaurantId);

        return MenuItemMapper.toDTO(menuItemRepository.save(item));
    }

    public MenuItemDTO updateMenuItem(String restaurantId, String menuId, MenuItemDTO dto) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhà hàng"));

        if (!categoryRepository.existsById(dto.getCategoryId())) {
            throw new ResourceNotFoundException("Không tìm thấy loại món ăn");
        }

        MenuItem item = menuItemRepository.findById(menuId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy món ăn"));

        item.setName(dto.getName());
        item.setDescription(dto.getDescription());
        item.setPrice(dto.getPrice());
        item.setCategoryId(dto.getCategoryId());
        item.setImageUrl(dto.getImageUrl());
        item.setRestaurantId(restaurantId);

        return MenuItemMapper.toDTO(menuItemRepository.save(item));
    }

    public void deleteMenuItem(String restaurantId, String menuId) {
        restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhà hàng"));

        MenuItem item = menuItemRepository.findById(menuId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy món ăn"));

        menuItemRepository.delete(item);
    }

    public List<MenuItemDTO> getMenuItemsByRestaurant(String restaurantId) {
        List<MenuItem> menuItems = menuItemRepository.findByRestaurantId(restaurantId);
        List<MenuItemDTO> menuItemDTOs = new ArrayList<>();

        for (MenuItem item : menuItems) {
            MenuItemDTO dto = MenuItemMapper.toDTO(item);
            List<Promotion> promotions = promotionRepository.findByMenuItemId(item.getId());

            for (Promotion promo : promotions) {
                if (isPromotionActive(promo)) {
                    dto.setPromotion(PromotionMapper.toDTO(promo));
                    break;
                }
            }

            menuItemDTOs.add(dto);
        }

        return menuItemDTOs;
    }

    public List<MenuItemDTO> getMenuItemsByRestaurantAndCategory(String restaurantId, String categoryId) {
        return menuItemRepository.findByRestaurantIdAndCategoryId(restaurantId, categoryId)
                .stream()
                .map(MenuItemMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<MenuItemDTO> searchMenuItems(String keyword) {
        return menuItemRepository.findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(MenuItemMapper::toDTO)
                .collect(Collectors.toList());
    }

    public MenuItemDTO getMenuItemById(String restaurantId, String menuId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy nhà hàng"));

        MenuItem item = menuItemRepository.findById(menuId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy món ăn"));

        MenuItemDTO dto = MenuItemMapper.toDTO(item);

        List<Promotion> promotions = promotionRepository.findByMenuItemId(menuId);
        for (Promotion promo : promotions) {
            if (isPromotionActive(promo)) {
                dto.setPromotion(PromotionMapper.toDTO(promo));
                break;
            }
        }

        return dto;
    }

    public boolean decreaseStock(String menuItemId, int quantity) {
        Optional<MenuItem> optionalMenuItem = menuItemRepository.findById(menuItemId);
        if (optionalMenuItem.isEmpty()) {
            System.out.println("❌ Không tìm thấy món có ID: " + menuItemId);
            return false;
        }

        MenuItem menuItem = optionalMenuItem.get();
        if (menuItem.getStock() < quantity) {
            System.out.println("⚠ Không đủ hàng trong kho cho món: " + menuItem.getName());
            return false;
        }

        menuItem.setStock(menuItem.getStock() - quantity);
        menuItemRepository.save(menuItem);
        System.out.println("✅ Đã trừ tồn kho cho món: " + menuItem.getName());
        return true;
    }

    public boolean existsById(String menuItemId) {
        return menuItemRepository.existsById(menuItemId);
    }

    public void increaseStock(String menuItemId, int quantity) {
        MenuItem menuItem = menuItemRepository.findById(menuItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy món ăn: " + menuItemId));

        int currentStock = menuItem.getStock();
        menuItem.setStock(currentStock + quantity);

        menuItemRepository.save(menuItem);

        System.out.println("🔄 Tăng tồn kho món: " + menuItem.getName()
                + " (" + menuItemId + "), +"
                + quantity + " → tổng: " + menuItem.getStock());
    }

    public List<MenuItemDTO> searchAdvanced(String keyword, String restaurantId, String categoryId,
                                            Double minPrice, Double maxPrice, Boolean hasPromotion) {
        List<MenuItem> items = menuItemRepository
                .searchAdvanced(keyword, restaurantId, categoryId, minPrice, maxPrice, null);
        // ❗ Gửi null ở đây để repo không lọc khuyến mãi sớm

        return items.stream()
                .map(item -> {
                    List<Promotion> promos = promotionRepository.findByMenuItemId(item.getId());
                    for (Promotion promo : promos) {
                        if (isPromotionActive(promo)) {
                            return MenuItemMapper.toDTO(item, PromotionMapper.toDTO(promo));
                        }
                    }

                    // ❗ Nếu yêu cầu chỉ lấy món có khuyến mãi
                    if (Boolean.TRUE.equals(hasPromotion)) {
                        return null; // bỏ qua món không có khuyến mãi
                    }

                    return MenuItemMapper.toDTO(item, null);
                })
                .filter(dto -> dto != null) // ✅ loại bỏ món không hợp lệ
                .collect(Collectors.toList());
    }


    public List<MenuItemDTO> getRecommendedMenuItems(int limit) {
        List<TopSoldItemDTO> topItems = orderClient.getTopMenuItems(limit);

        return topItems.stream()
                .map(top -> {
                    MenuItem item = menuItemRepository.findById(top.getMenuItemId()).orElse(null);
                    if (item == null) return null;

                    PromotionDTO promotion = null;
                    for (Promotion p : promotionRepository.findByMenuItemId(item.getId())) {
                        if (isPromotionActive(p)) {
                            promotion = PromotionMapper.toDTO(p);
                            break;
                        }
                    }

                    return MenuItemMapper.toDTO(item, promotion);
                })
                .filter(dto -> dto != null)
                .toList();
    }



}