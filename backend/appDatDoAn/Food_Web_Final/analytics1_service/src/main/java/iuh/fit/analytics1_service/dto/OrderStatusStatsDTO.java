package iuh.fit.analytics1_service.dto;

import lombok.Data;

@Data
public class OrderStatusStatsDTO {
    private long totalOrders;
    private long delivered;
    private long canceled;
}
