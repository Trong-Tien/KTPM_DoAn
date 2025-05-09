package iuh.fit.auth_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
public class LoginRequest {
    private String username;
    private String password;
}
