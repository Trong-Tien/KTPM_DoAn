package iuh.fit.restaurant_service.service;

import iuh.fit.restaurant_service.exception.ResourceNotFoundException;
import iuh.fit.restaurant_service.model.Category;
import iuh.fit.restaurant_service.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    public List<Category> getCategoriesByRestaurant(String restaurantId) {
        return categoryRepository.findByRestaurantId(restaurantId);
    }

    public Category getCategoryById(String restaurantId, String categoryId) {
        return categoryRepository.findByRestaurantIdAndId(restaurantId, categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
    }

    public Category updateCategory(String restaurantId, String categoryId, Category updatedCategory) {
        Category existingCategory = categoryRepository.findByRestaurantIdAndId(restaurantId, categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        existingCategory.setName(updatedCategory.getName());
        return categoryRepository.save(existingCategory);
    }

    public void deleteCategory(String restaurantId, String categoryId) {
        Category category = categoryRepository.findByRestaurantIdAndId(restaurantId, categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        categoryRepository.delete(category);
    }
}

