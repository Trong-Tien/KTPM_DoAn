package iuh.fit.restaurant_service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class MenuItemDTO {
    @Id
    private String id;
    @NotBlank(message = "Tên món ăn không được để trống")
    @Size(min = 3, max = 100, message = "Tên món ăn phải từ 3 đến 100 ký tự")
    private String name;

    @NotBlank(message = "Mô tả món ăn không được để trống")
    private String description;

    @NotNull(message = "Giá món ăn không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá món ăn phải lớn hơn 0")
    private Double price;

    @NotBlank(message = "Ảnh món ăn không được để trống")
    private String imageUrl;

    @NotBlank(message = "Phải chọn loại món ăn")
    private String categoryId;

    @NotBlank(message = "Phải chọn nhà hàng")
    private String restaurantId;

    @Min(value = 0, message = "Số lượng món ăn phải lớn hơn hoặc bằng 0")
    private int stock;

    private PromotionDTO promotion;
}
