package iuh.fit.restaurant_service.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PromotionDTO {
    private String id;

    @NotBlank(message = "Phải chọn món ăn áp dụng khuyến mãi")
    private String menuItemId;

    @NotNull(message = "Phần trăm giảm giá không được để trống")
    @Min(value = 1, message = "Phần trăm giảm giá tối thiểu là 1%")
    @Max(value = 100, message = "Phần trăm giảm giá tối đa là 100%")
    private double discountPercent;

    @NotNull(message = "Ngày bắt đầu không được để trống")
    private LocalDate startDate;

    @NotNull(message = "Ngày kết thúc không được để trống")
    private LocalDate endDate;
}
