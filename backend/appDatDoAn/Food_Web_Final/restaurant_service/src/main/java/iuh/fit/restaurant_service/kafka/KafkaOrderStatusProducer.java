package iuh.fit.restaurant_service.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaOrderStatusProducer {

    private final KafkaTemplate<String, OrderStatusUpdateEvent> kafkaTemplate;

    public void sendOrderStatus(OrderStatusUpdateEvent event) {
        kafkaTemplate.send("order-status-updated", event);
        System.out.println("✅ Gửi trạng thái đơn hàng Kafka: " + event);
    }
}