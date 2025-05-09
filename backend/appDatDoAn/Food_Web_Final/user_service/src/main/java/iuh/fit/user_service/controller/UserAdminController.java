package iuh.fit.user_service.controller;

import iuh.fit.user_service.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin/user")
@PreAuthorize("hasAuthority('ROLE_ADMIN')")
@RequiredArgsConstructor
public class UserAdminController {

    private final UserService userService;

    @GetMapping("/count")
    public ResponseEntity<Long> countAllUsers() {
        return ResponseEntity.ok(userService.countAllUsers());
    }
}

