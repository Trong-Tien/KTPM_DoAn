package iuh.fit.analytics1_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "userservice")
public interface UserClient {
    @GetMapping("/api/admin/user/count")
    long countAllUsers();
}
