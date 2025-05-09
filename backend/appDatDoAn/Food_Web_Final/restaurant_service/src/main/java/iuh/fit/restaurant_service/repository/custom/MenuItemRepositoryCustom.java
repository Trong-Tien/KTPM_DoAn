package iuh.fit.restaurant_service.repository.custom;

import iuh.fit.restaurant_service.model.MenuItem;

import java.util.List;

public interface MenuItemRepositoryCustom {
    List<MenuItem> searchAdvanced(String keyword, String restaurantId, String categoryId,
                                  Double minPrice, Double maxPrice, Boolean hasPromotion);
}

