package iuh.fit.restaurant_service.controller;

import iuh.fit.restaurant_service.dto.MenuItemDTO;
import iuh.fit.restaurant_service.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant")
@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ROLE_USER', 'ROLE_ADMIN')")
public class MenuItemUserController {

    private final MenuItemService menuItemService;

    @GetMapping("/menu/{menuItemId}/exists")
    public ResponseEntity<Boolean> checkMenuItemExists(@PathVariable String menuItemId) {
        boolean exists = menuItemService.existsById(menuItemId);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/{restaurantId}/menu")
    public ResponseEntity<List<MenuItemDTO>> getMenuItems(@PathVariable String restaurantId,
                                                          @RequestParam(required = false) String categoryId) {
        List<MenuItemDTO> items = (categoryId != null)
                ? menuItemService.getMenuItemsByRestaurantAndCategory(restaurantId, categoryId)
                : menuItemService.getMenuItemsByRestaurant(restaurantId);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/menu/search")
    public ResponseEntity<List<MenuItemDTO>> searchMenuItems(@RequestParam String keyword) {
        List<MenuItemDTO> items = menuItemService.searchMenuItems(keyword);
        return ResponseEntity.ok(items);
    }

    @GetMapping("/{restaurantId}/menu/{menuId}")
    public ResponseEntity<MenuItemDTO> getMenuItemById(@PathVariable String restaurantId,
                                                       @PathVariable String menuId) {
        MenuItemDTO item = menuItemService.getMenuItemById(restaurantId, menuId);
        return ResponseEntity.ok(item);
    }

    @GetMapping("/menu/search-advanced")
    public ResponseEntity<List<MenuItemDTO>> searchMenuAdvanced(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String restaurantId,
            @RequestParam(required = false) String categoryId,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) Boolean hasPromotion
    ) {
        return ResponseEntity.ok(
                menuItemService.searchAdvanced(keyword, restaurantId, categoryId, minPrice, maxPrice, hasPromotion)
        );
    }

    @GetMapping("/menu/recommend")
    public ResponseEntity<List<MenuItemDTO>> recommend(@RequestParam(defaultValue = "5") int limit) {
        return ResponseEntity.ok(menuItemService.getRecommendedMenuItems(limit));
    }


}
