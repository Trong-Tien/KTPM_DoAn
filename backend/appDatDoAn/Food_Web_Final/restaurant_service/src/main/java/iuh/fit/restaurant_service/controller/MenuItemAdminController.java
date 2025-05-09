package iuh.fit.restaurant_service.controller;

import iuh.fit.restaurant_service.dto.MenuItemDTO;
import iuh.fit.restaurant_service.repository.MenuItemRepository;
import iuh.fit.restaurant_service.service.MenuItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/restaurant")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class MenuItemAdminController {

    private final MenuItemService menuItemService;
    private final MenuItemRepository menuItemRepository;

    //Thêm danh sách món ăn
    @PostMapping("/{restaurantId}/menu")
    public ResponseEntity<MenuItemDTO> createMenuItem(@PathVariable String restaurantId,
                                                      @Valid @RequestBody MenuItemDTO menuItemDTO) {
        MenuItemDTO created = menuItemService.createMenuItem(restaurantId, menuItemDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    //Cập nhật món ăn
    @PutMapping("/{restaurantId}/menu/{menuId}")
    public ResponseEntity<MenuItemDTO> updateMenuItem(@PathVariable String restaurantId,
                                                      @PathVariable String menuId,
                                                      @Valid @RequestBody MenuItemDTO menuItemDTO) {
        MenuItemDTO updated = menuItemService.updateMenuItem(restaurantId, menuId, menuItemDTO);
        return ResponseEntity.ok(updated);
    }

    //Xoa món ăn
    @DeleteMapping("/{restaurantId}/menu/{menuId}")
    public ResponseEntity<Void> deleteMenuItem(@PathVariable String restaurantId,
                                               @PathVariable String menuId) {
        menuItemService.deleteMenuItem(restaurantId, menuId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/menu/count")
    public ResponseEntity<Long> countMenuItems() {
        return ResponseEntity.ok(menuItemRepository.count());
    }
}
