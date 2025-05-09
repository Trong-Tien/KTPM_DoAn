package iuh.fit.user_service.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LinkBankAccountRequest {
    @NotBlank
    private String bankName;

    @NotBlank
    private String accountNumber;
}
