package iuh.fit.auth_service.controller;

import iuh.fit.auth_service.security.JwtTokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(org.springframework.security.core.Authentication authentication) {
        String username = authentication.getName();
        String role = authentication.getAuthorities().toString();

        return ResponseEntity.ok("Hello " + username + ", your role is: " + role);
    }

}
