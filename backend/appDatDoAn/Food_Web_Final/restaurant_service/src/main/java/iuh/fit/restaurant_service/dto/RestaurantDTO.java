package iuh.fit.restaurant_service.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class RestaurantDTO {
    private String id;
    @NotBlank(message = "Tên nhà hàng không được để trống")
    @Size(min = 3, max = 100, message = "Tên nhà hàng phải từ 3 đến 100 ký tự")
    private String name;

    @NotBlank(message = "Địa chỉ không được để trống")
    private String address;

    @NotBlank(message = "Email không được để trống")
    @Email(message = "Định dạng email không hợp lệ")
    private String email;

    @Size(min = 10, max = 10, message = "Số điện thoại phải có 10 ký tự")
    @NotBlank(message = "Số điện thoại không được để trống")
    private String phone;

    @NotBlank(message = "Mô tả không được để trống")
    private String description;

    @NotBlank(message = "Ảnh của nhà hàng không được để trống")
    private String imageUrl;

    @NotBlank(message = "Ảnh của nhà hàng không được để trống")
    private String openingHours;

    @NotNull(message = "Không được để trống")
    private Double latitude;

    @NotNull(message = "Không được để trống")
    private Double longitude;
    @NotBlank(message = "Nhà hàng phải có người quản lý")
    private String managerId; // ID của user có role ROLE_MANAGER

    @NotNull(message = "Hoa hồng không được để trống")
    @DecimalMin(value = "0.0", inclusive = true, message = "Hoa hồng phải ≥ 0")
    @DecimalMax(value = "1.0", inclusive = true, message = "Hoa hồng phải ≤ 1.0 (tức 100%)")
    private Double commissionRate; // VD: 0.1 = 10%



}
