package iuh.fit.restaurant_service.client;

import iuh.fit.restaurant_service.dto.TopSoldItemDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "orderservice")
public interface OrderClient {
    @GetMapping("/api/order/check-reviewed")
    boolean hasUserOrderedAndReceived(@RequestParam String userId, @RequestParam String menuItemId);

    @GetMapping("/api/public/order/top-menu-items")
    List<TopSoldItemDTO> getTopMenuItems(@RequestParam(defaultValue = "5") int limit);
}


