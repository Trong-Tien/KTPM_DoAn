package iuh.fit.analytics1_service.dto;

import lombok.Data;

import java.util.List;

@Data
public class ManagerOverviewDTO {
    private double totalRevenue;
    private double platformProfit;
    private double restaurantRevenue;

    private long totalOrders;
    private long delivered;
    private long canceled;

    private List<TopSoldItemDTO> topItems;
    private List<DailyRevenueDTO> dailyRevenue;
}
