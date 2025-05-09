package iuh.fit.restaurant_service.dto;

import lombok.Data;

@Data
public class TopSoldItemDTO {
    private String menuItemId;
    private long totalSold;
}
