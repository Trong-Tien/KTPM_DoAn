package iuh.fit.analytics1_service.controller;

import iuh.fit.analytics1_service.client.OrderClient;
import iuh.fit.analytics1_service.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/manager/analytics")
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_MANAGER')")
public class AnalyticsManagerController {

    private final OrderClient orderClient;

    @GetMapping("/revenue")
    public ResponseEntity<RevenueStatsDTO> getRevenueByRestaurant(
            @RequestParam String restaurantId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to) {

        RevenueStatsDTO stats = orderClient.getRevenueStatsByRestaurant(
                restaurantId,
                from.toString(),
                to.toString()
        );
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/order-stats")
    public ResponseEntity<OrderStatusStatsDTO> getOrderStatusStats(
            @RequestParam String restaurantId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        OrderStatusStatsDTO dto = orderClient.getOrderStatusStats(restaurantId, from.toString(), to.toString());
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/top-menu-items")
    public ResponseEntity<List<TopSoldItemDTO>> getTopSellingItems(
            @RequestParam String restaurantId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(defaultValue = "5") int limit
    ) {
        return ResponseEntity.ok(
                orderClient.getTopMenuItems(restaurantId, from.toString(), to.toString(), limit)
        );
    }

    @GetMapping("/revenue-by-day")
    public ResponseEntity<List<DailyRevenueDTO>> getRevenueByDay(
            @RequestParam String restaurantId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return ResponseEntity.ok(
                orderClient.getRevenueByDay(restaurantId, from.toString(), to.toString())
        );
    }

    @GetMapping("/overview")
    public ResponseEntity<ManagerOverviewDTO> getManagerOverview(
            @RequestParam String restaurantId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to,
            @RequestParam(defaultValue = "5") int limit
    ) {
        RevenueStatsDTO revenue = orderClient.getRevenueStatsByRestaurant(restaurantId, from.toString(), to.toString());
        OrderStatusStatsDTO status = orderClient.getOrderStatusStats(restaurantId, from.toString(), to.toString());
        List<TopSoldItemDTO> topItems = orderClient.getTopMenuItems(restaurantId, from.toString(), to.toString(), limit);
        List<DailyRevenueDTO> revenueByDay = orderClient.getRevenueByDay(restaurantId, from.toString(), to.toString());

        ManagerOverviewDTO dto = new ManagerOverviewDTO();
        dto.setTotalRevenue(revenue.getTotalRevenue());
        dto.setPlatformProfit(revenue.getPlatformProfit());
        dto.setRestaurantRevenue(revenue.getRestaurantRevenue());

        dto.setTotalOrders(status.getTotalOrders());
        dto.setDelivered(status.getDelivered());
        dto.setCanceled(status.getCanceled());

        dto.setTopItems(topItems);
        dto.setDailyRevenue(revenueByDay);

        return ResponseEntity.ok(dto);
    }



}
