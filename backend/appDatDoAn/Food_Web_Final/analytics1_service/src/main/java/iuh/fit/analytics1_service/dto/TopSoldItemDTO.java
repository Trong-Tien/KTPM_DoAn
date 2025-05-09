package iuh.fit.analytics1_service.dto;

import lombok.Data;

@Data
public class TopSoldItemDTO {
    private String menuItemId;
    private String name;
    private long totalSold;
}
