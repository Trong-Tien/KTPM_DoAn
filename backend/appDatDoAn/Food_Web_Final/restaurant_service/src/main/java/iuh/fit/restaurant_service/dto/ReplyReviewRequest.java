package iuh.fit.restaurant_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyReviewRequest {
    @NotBlank(message = "Nội dung phản hồi không được để trống")
    private String reply;
}
