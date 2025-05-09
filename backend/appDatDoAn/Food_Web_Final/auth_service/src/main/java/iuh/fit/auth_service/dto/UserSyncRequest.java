    package iuh.fit.auth_service.dto;

    import lombok.Getter;
    import lombok.Setter;

    @Getter
    @Setter
    public class UserSyncRequest {
        private String username;
        private String email;
        private String role;
    }
