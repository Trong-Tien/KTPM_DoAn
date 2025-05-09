package iuh.fit.restaurant_service.kafka;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusUpdateEvent {
    private String orderId;
    private String status;

    private List<OrderItemKafka> items; // ✅ danh sách món bị huỷ để hoàn kho

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemKafka {
        private String menuItemId;
        private int quantity;
    }
}