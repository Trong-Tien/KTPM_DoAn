package iuh.fit.auth_service.dto;

import lombok.Data;

@Data
public class SyncUserRequest {
    private String username;
    private String email;
    private String role;
}
