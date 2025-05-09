package iuh.fit.restaurant_service.controller;

import iuh.fit.restaurant_service.model.Category;
import iuh.fit.restaurant_service.model.Restaurant;
import iuh.fit.restaurant_service.security.JwtTokenUtil;
import iuh.fit.restaurant_service.service.CategoryService;
import iuh.fit.restaurant_service.service.RestaurantService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/manager/restaurant/{restaurantId}/category")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_MANAGER')")
public class CategoryManagerController {

    private final CategoryService categoryService;
    private final RestaurantService restaurantService;
    private final JwtTokenUtil jwtTokenUtil;

    @PostMapping
    public ResponseEntity<?> createCategory(@PathVariable String restaurantId,
                                            @Valid @RequestBody Category category,
                                            HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String managerId = jwtTokenUtil.getUsername(token);

        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        if (!restaurant.getManagerId().equals(managerId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn không có quyền tạo category cho nhà hàng này.");
        }

        category.setRestaurantId(restaurantId);
        Category created = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{categoryId}")
    public ResponseEntity<?> updateCategory(@PathVariable String restaurantId, 
                                          @PathVariable String categoryId, 
                                          @Valid @RequestBody Category category,
                                          HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String managerId = jwtTokenUtil.getUsername(token);

        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        if (!restaurant.getManagerId().equals(managerId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn không có quyền cập nhật category cho nhà hàng này.");
        }

        category.setRestaurantId(restaurantId);
        Category updatedCategory = categoryService.updateCategory(restaurantId, categoryId, category);
        return ResponseEntity.ok(updatedCategory);
    }

    // Xóa loại món ăn
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<?> deleteCategory(@PathVariable String restaurantId, 
                                          @PathVariable String categoryId,
                                          HttpServletRequest request) {
        String token = request.getHeader("Authorization").substring(7);
        String managerId = jwtTokenUtil.getUsername(token);

        Restaurant restaurant = restaurantService.getRestaurantById(restaurantId);
        if (!restaurant.getManagerId().equals(managerId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Bạn không có quyền xóa category của nhà hàng này.");
        }

        categoryService.deleteCategory(restaurantId, categoryId);
        return ResponseEntity.noContent().build();
    }
}
