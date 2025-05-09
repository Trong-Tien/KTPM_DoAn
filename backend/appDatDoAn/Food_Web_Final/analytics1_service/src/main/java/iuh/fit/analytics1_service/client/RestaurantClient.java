package iuh.fit.analytics1_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "restaurantservice")
public interface RestaurantClient {
    @GetMapping("/api/admin/restaurant/count")
    long countRestaurants();

    @GetMapping("/api/admin/restaurant/menu/count")
    long countMenuItems();
}

