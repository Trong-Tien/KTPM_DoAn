package iuh.fit.restaurant_service.repository.custom;

import iuh.fit.restaurant_service.model.MenuItem;
import iuh.fit.restaurant_service.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class MenuItemRepositoryImpl implements MenuItemRepositoryCustom {

    private final MongoTemplate mongoTemplate;
    private final PromotionRepository promotionRepository;

    @Override
    public List<MenuItem> searchAdvanced(String keyword, String restaurantId, String categoryId,
                                         Double minPrice, Double maxPrice, Boolean hasPromotion) {

        List<Criteria> criteriaList = new ArrayList<>();

        if (keyword != null && !keyword.isBlank()) {
            criteriaList.add(Criteria.where("name").regex(keyword, "i"));
        }

        if (restaurantId != null && !restaurantId.isBlank()) {
            criteriaList.add(Criteria.where("restaurantId").is(restaurantId));
        }

        if (categoryId != null && !categoryId.isBlank()) {
            criteriaList.add(Criteria.where("categoryId").is(categoryId));
        }

        if (minPrice != null || maxPrice != null) {
            Criteria priceCriteria = Criteria.where("price");
            if (minPrice != null) priceCriteria.gte(minPrice);
            if (maxPrice != null) priceCriteria.lte(maxPrice);
            criteriaList.add(priceCriteria);
        }

        Query query;
        if (criteriaList.isEmpty()) {
            query = new Query(); // truy vấn tất cả
        } else {
            query = new Query(new Criteria().andOperator(criteriaList.toArray(new Criteria[0])));
        }

        List<MenuItem> items = mongoTemplate.find(query, MenuItem.class);

// ✅ Lọc theo promotion (nếu được yêu cầu)
        if (Boolean.TRUE.equals(hasPromotion)) {
            return items.stream()
                    .filter(item -> promotionRepository.existsByMenuItemId(item.getId()))
                    .toList();
        }

        return items;
    }

}
