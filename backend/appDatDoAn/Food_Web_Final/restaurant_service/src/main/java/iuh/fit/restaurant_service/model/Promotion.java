    package iuh.fit.restaurant_service.model;

    import jakarta.validation.constraints.*;
    import lombok.Data;
    import lombok.Getter;
    import lombok.Setter;
    import org.springframework.data.annotation.Id;
    import org.springframework.data.mongodb.core.mapping.Document;

    import java.time.LocalDate;
    import java.time.LocalDateTime;

    @Getter
    @Setter
    @Document(collection = "promotions")
    @Data
    public class Promotion {
        @Id
        private String id;
        private String name;
        private double discountPercent;
        private LocalDate startDate;
        private LocalDate endDate;
        private String menuItemId;
        private String restaurantId;
    }