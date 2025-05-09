package iuh.fit.restaurant_service.controller;

import iuh.fit.restaurant_service.model.Category;
import iuh.fit.restaurant_service.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant/{restaurantId}/category")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_USER')")
public class CategoryUserController {

    private final CategoryService categoryService;

    // Lấy danh sách loại món ăn
    @GetMapping
    public ResponseEntity<List<Category>> getCategories(@PathVariable String restaurantId) {
        List<Category> categories = categoryService.getCategoriesByRestaurant(restaurantId);
        return ResponseEntity.ok(categories);
    }

    // Lấy thông tin loại món ăn
    @GetMapping("/{categoryId}")
    public ResponseEntity<Category> getCategoryById(@PathVariable String restaurantId, @PathVariable String categoryId) {
        Category category = categoryService.getCategoryById(restaurantId, categoryId);
        return ResponseEntity.ok(category);
    }
}