package iuh.fit.analytics1_service.client;

import iuh.fit.analytics1_service.dto.*;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(
        name = "orderservice",
        configuration = iuh.fit.analytics1_service.security.FeignClientConfig.class
)

public interface OrderClient {
    @GetMapping("/api/admin/order/stats")
    SystemStatsDTO getOrderStats(); // Gồm totalOrders, totalRevenue, platformProfit


    @GetMapping("/api/manager/order/revenue")
    RevenueStatsDTO getRevenueStatsByRestaurant(
            @RequestParam("restaurantId") String restaurantId,
            @RequestParam("from") String from,
            @RequestParam("to") String to
    );

    @GetMapping("/api/manager/order/stats")
    OrderStatusStatsDTO getOrderStatusStats(
            @RequestParam("restaurantId") String restaurantId,
            @RequestParam("from") String from,
            @RequestParam("to") String to
    );

    @GetMapping("/api/manager/order/top-menu-items")
    List<TopSoldItemDTO> getTopMenuItems(
            @RequestParam("restaurantId") String restaurantId,
            @RequestParam("from") String from,
            @RequestParam("to") String to,
            @RequestParam("limit") int limit
    );

    @GetMapping("/api/manager/order/revenue-by-day")
    List<DailyRevenueDTO> getRevenueByDay(
            @RequestParam("restaurantId") String restaurantId,
            @RequestParam("from") String from,
            @RequestParam("to") String to
    );

    @GetMapping("/api/admin/order/top-restaurants")
    List<TopRestaurantDTO> getTopRestaurants(
            @RequestParam("from") String from,
            @RequestParam("to") String to,
            @RequestParam("limit") int limit
    );

    @GetMapping("/api/admin/order/revenue-by-day")
    List<DailyRevenueDTO> getSystemRevenueByDay(
            @RequestParam("from") String from,
            @RequestParam("to") String to
    );

    @GetMapping("/api/admin/order/revenue-by-month")
    List<PeriodRevenueDTO> getSystemRevenueByMonth(
            @RequestParam("from") String from, // dạng yyyy-MM
            @RequestParam("to") String to
    );

    @GetMapping("/api/admin/order/revenue-by-year")
    List<PeriodRevenueDTO> getSystemRevenueByYear(
            @RequestParam("from") String from, // dạng yyyy
            @RequestParam("to") String to
    );

}



