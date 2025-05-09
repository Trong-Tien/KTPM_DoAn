package iuh.fit.analytics1_service.dto;

import lombok.Data;

@Data
public class TopRestaurantDTO {
    private String restaurantId;
    private String restaurantName;
    private double totalRevenue;
}
