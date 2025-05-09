package iuh.fit.analytics1_service.dto;

import lombok.Data;

@Data
public class SystemStatsDTO {
    private long totalUsers;
    private long totalRestaurants;
    private long totalMenuItems;
    private long totalOrders;
    private double totalRevenue;
    private double platformProfit;
}