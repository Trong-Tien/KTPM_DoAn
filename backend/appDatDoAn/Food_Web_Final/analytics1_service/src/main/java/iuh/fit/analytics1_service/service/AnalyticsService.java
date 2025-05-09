package iuh.fit.analytics1_service.service;

import iuh.fit.analytics1_service.client.OrderClient;
import iuh.fit.analytics1_service.client.RestaurantClient;
import iuh.fit.analytics1_service.client.UserClient;
import iuh.fit.analytics1_service.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final OrderClient orderClient;
    private final RestaurantClient restaurantClient;
    private final UserClient userClient;

    public SystemStatsDTO getSystemStats() {
        SystemStatsDTO stats = orderClient.getOrderStats();

        stats.setTotalUsers(userClient.countAllUsers());
        stats.setTotalRestaurants(restaurantClient.countRestaurants());
        stats.setTotalMenuItems(restaurantClient.countMenuItems());

        return stats;
    }

    public ManagerOverviewDTO getOverviewForRestaurant(String restaurantId, LocalDate from, LocalDate to, int limit) {
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

        return dto;
    }

    public List<TopRestaurantDTO> getTopRestaurants(LocalDate from, LocalDate to, int limit) {
        return orderClient.getTopRestaurants(from.toString(), to.toString(), limit);
    }

    public List<DailyRevenueDTO> getSystemRevenueByDay(LocalDate from, LocalDate to) {
        return orderClient.getSystemRevenueByDay(from.toString(), to.toString());
    }

    public List<PeriodRevenueDTO> getSystemRevenueByMonth(String from, String to) {
        return orderClient.getSystemRevenueByMonth(from, to);
    }

    public List<PeriodRevenueDTO> getSystemRevenueByYear(String from, String to) {
        return orderClient.getSystemRevenueByYear(from, to);
    }



}
