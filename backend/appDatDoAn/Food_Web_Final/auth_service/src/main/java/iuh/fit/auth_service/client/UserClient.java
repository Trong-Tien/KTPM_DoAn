package iuh.fit.auth_service.client;

import iuh.fit.auth_service.dto.SyncUserRequest;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "userservice")
public interface UserClient {
    @PostMapping("/api/user/sync")
    ResponseEntity<String> syncUser(@RequestBody SyncUserRequest request);
}
