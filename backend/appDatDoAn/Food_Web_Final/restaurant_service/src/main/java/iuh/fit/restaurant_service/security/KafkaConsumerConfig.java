package iuh.fit.restaurant_service.security;

import iuh.fit.restaurant_service.kafka.OrderCreatedEvent;
import iuh.fit.restaurant_service.kafka.OrderStatusUpdateEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, OrderCreatedEvent> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, OrderCreatedEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, OrderCreatedEvent> consumerFactory() {
        JsonDeserializer<OrderCreatedEvent> deserializer = new JsonDeserializer<>(OrderCreatedEvent.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "restaurant-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }

    @Bean(name = "orderStatusKafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, OrderStatusUpdateEvent> orderStatusKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, OrderStatusUpdateEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(orderStatusConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, OrderStatusUpdateEvent> orderStatusConsumerFactory() {
        JsonDeserializer<OrderStatusUpdateEvent> deserializer = new JsonDeserializer<>(OrderStatusUpdateEvent.class, false);
        deserializer.addTrustedPackages("*");
        deserializer.setRemoveTypeHeaders(true);

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers); // ✅
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "restaurant-group");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), deserializer);
    }
}
