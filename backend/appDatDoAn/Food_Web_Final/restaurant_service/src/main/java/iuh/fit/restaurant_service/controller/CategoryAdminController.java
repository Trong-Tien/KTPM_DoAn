package iuh.fit.restaurant_service.controller;

import iuh.fit.restaurant_service.model.Category;
import iuh.fit.restaurant_service.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/restaurant/{restaurantId}/category")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
public class CategoryAdminController {

    private final CategoryService categoryService;

    // Tạo danh mục loại món ăn
    @PostMapping
    public ResponseEntity<Category> createCategory(@PathVariable String restaurantId, @Valid @RequestBody Category category) {
        category.setRestaurantId(restaurantId);
        Category createdCategory = categoryService.createCategory(category);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCategory);
    }

    // Cập nhật loại món ăn
    @PutMapping("/{categoryId}")
    public ResponseEntity<Category> updateCategory(@PathVariable String restaurantId, @PathVariable String categoryId, @Valid @RequestBody Category category) {
        Category updatedCategory = categoryService.updateCategory(restaurantId, categoryId, category);
        return ResponseEntity.ok(updatedCategory);
    }

    // Xóa loại món ăn
    @DeleteMapping("/{categoryId}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String restaurantId, @PathVariable String categoryId) {
        categoryService.deleteCategory(restaurantId, categoryId);
        return ResponseEntity.noContent().build();
    }
}