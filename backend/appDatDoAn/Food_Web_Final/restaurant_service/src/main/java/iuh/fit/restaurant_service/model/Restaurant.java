package iuh.fit.restaurant_service.model;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

@Getter
@Setter
@Document(collection = "restaurants")
public class Restaurant {

    @Id
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

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private GeoJsonPoint location;
    // Getters and Setters

    @NotBlank(message = "Nhà hàng phải có người quản lý")
    private String managerId; // ID của user có role ROLE_MANAGER

    @NotNull(message = "Hoa hồng không được để trống")
    @DecimalMin(value = "0.0", inclusive = true, message = "Hoa hồng phải ≥ 0")
    @DecimalMax(value = "1.0", inclusive = true, message = "Hoa hồng phải ≤ 1.0 (tức 100%)")
    private Double commissionRate; // VD: 0.1 = 10%

}
