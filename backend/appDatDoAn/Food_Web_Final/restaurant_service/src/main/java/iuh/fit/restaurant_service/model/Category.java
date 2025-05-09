package iuh.fit.restaurant_service.model;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "categories")
public class Category {

    @Id
    private String id;

    @NotBlank(message = "Tên loại món ăn không được để trống")
    private String name;

    private String restaurantId;  // ID của nhà hàng mà loại món ăn này thuộc về

    // Các trường khác nếu cần

}
