package iuh.fit.restaurant_service.kafka;

import iuh.fit.restaurant_service.service.MenuItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class KafkaOrderConsumer {

    private final MenuItemService menuItemService;
    private final KafkaOrderStatusProducer statusProducer;

    @KafkaListener(topics = "order-created", groupId = "restaurant-group", containerFactory = "kafkaListenerContainerFactory")
    public void handleOrderCreated(OrderCreatedEvent event) {
        System.out.println("🍽 Nhận đơn hàng mới từ Kafka:");
        System.out.println("➡ ID đơn: " + event.getOrderId());
        System.out.println("➡ Nhà hàng: " + event.getRestaurantId());
        System.out.println("➡ Món ăn: " + event.getItems());

        boolean allSuccess = true;
        for (OrderCreatedEvent.OrderItemKafka item : event.getItems()) {
            boolean success = menuItemService.decreaseStock(item.getMenuItemId(), item.getQuantity());
            if (!success) allSuccess = false;
        }

        if (!allSuccess) {
            // ✅ Chuyển danh sách từ OrderCreatedEvent.OrderItemKafka -> OrderStatusUpdateEvent.OrderItemKafka
            List<OrderStatusUpdateEvent.OrderItemKafka> convertedItems = event.getItems().stream()
                    .map(i -> new OrderStatusUpdateEvent.OrderItemKafka(i.getMenuItemId(), i.getQuantity()))
                    .toList();

            statusProducer.sendOrderStatus(
                    new OrderStatusUpdateEvent(event.getOrderId(), "FAILED", convertedItems)
            );
        }

        // ✅ Nếu còn hàng → KHÔNG gửi CONFIRMED, chờ admin duyệt thủ công
    }

    @KafkaListener(topics = "order-status-updated", groupId = "restaurant-group", containerFactory = "orderStatusKafkaListenerContainerFactory")
    public void handleOrderCanceled(OrderStatusUpdateEvent event) {
        System.out.println("🛑 Nhận trạng thái đơn hàng: " + event.getStatus() + " cho đơn: " + event.getOrderId());

        if ("CANCELED".equalsIgnoreCase(event.getStatus())) {
            for (OrderStatusUpdateEvent.OrderItemKafka item : event.getItems()) {
                menuItemService.increaseStock(item.getMenuItemId(), item.getQuantity());
            }
            System.out.println("✅ Đã hoàn kho " + event.getItems().size() + " món cho đơn hàng bị huỷ: " + event.getOrderId());
        }
    }
}
