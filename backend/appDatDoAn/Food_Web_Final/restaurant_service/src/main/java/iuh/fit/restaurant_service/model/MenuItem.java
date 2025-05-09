package iuh.fit.restaurant_service.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "menuItems")
public class MenuItem {

    @Id
    private String id;
    private String name;
    private String description;
    private Double price;
    private String imageUrl;
    private String restaurantId;
    private String categoryId;

    private int stock;
}
