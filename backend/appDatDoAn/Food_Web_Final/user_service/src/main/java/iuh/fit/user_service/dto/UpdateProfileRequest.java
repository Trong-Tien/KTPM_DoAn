package iuh.fit.user_service.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateProfileRequest {

    @NotBlank(message = "Họ tên không được để trống")
    private String fullName;

    @NotBlank(message = "Giới tính không được để trống")
    private String gender;

    @NotNull(message = "Ngày sinh không được để trống")
    @Past(message = "Ngày sinh phải là ngày trong quá khứ")
    private LocalDate dateOfBirth;

    @Pattern(regexp = "^(0[0-9]{9})$", message = "Số điện thoại phải gồm 10 chữ số và bắt đầu bằng số 0")
    private String phone;

    @NotBlank(message = "Địa chỉ không được để trống")
    private String address;
}
