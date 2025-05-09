package iuh.fit.user_service.controller;

import iuh.fit.user_service.dto.SyncUserRequest;
import iuh.fit.user_service.dto.UpdateProfileRequest;
import iuh.fit.user_service.dto.UserInfoResponse;
import iuh.fit.user_service.model.User;
import iuh.fit.user_service.security.JwtTokenUtil;
import iuh.fit.user_service.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*")
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;


    @GetMapping("/{username}/exists")
    public ResponseEntity<Boolean> checkUserExists(@PathVariable String username) {
        return ResponseEntity.ok(userService.checkUserExists(username));
    }


    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
        System.out.println("✅ Auth: " + SecurityContextHolder.getContext().getAuthentication());
        System.out.println("✅ Authorities: " + SecurityContextHolder.getContext().getAuthentication().getAuthorities());
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Missing token");
        }

        String token = authHeader.substring(7);
        UserInfoResponse info = userService.getUserInfoFromToken(token);
        return ResponseEntity.ok(info);
    }
    @PostMapping("/sync")
    public ResponseEntity<String> syncUser(@RequestBody SyncUserRequest request) {
        try {
            logger.info("Received sync request for user: {}", request.getUsername());
            
            User user = new User();
            user.setUsername(request.getUsername());
            user.setEmail(request.getEmail());
            user.setRole(request.getRole());

            boolean created = userService.saveUserIfNotExists(user);
            if (!created) {
                logger.warn("User already exists: {}", request.getUsername());
                return ResponseEntity.badRequest().body("User already exists");
            }
            
            logger.info("User synced successfully: {}", request.getUsername());
            return ResponseEntity.ok("User synced successfully");
        } catch (Exception e) {
            logger.error("Error syncing user: {}", request.getUsername(), e);
            return ResponseEntity.internalServerError().body("Error syncing user: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<?> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @DeleteMapping("/{username}")
    public ResponseEntity<String> deleteUser(@PathVariable String username) {
        boolean deleted = userService.deleteUserByUsername(username);
        if (!deleted) {
            return ResponseEntity.status(404).body("Không tìm thấy người dùng");
        }
        return ResponseEntity.ok("Xóa người dùng thành công");
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(HttpServletRequest request, @Valid @RequestBody UpdateProfileRequest updateRequest) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Missing token");
        }
        String token = authHeader.substring(7);
        String username = JwtTokenUtil.getUsername(token);

        userService.updateProfile(username, updateRequest);
        return ResponseEntity.ok("Profile updated successfully");
    }
    @PostMapping("/profile")
    public ResponseEntity<?> createProfile(HttpServletRequest request, @Valid @RequestBody UpdateProfileRequest profileRequest) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401).body("Missing token");
        }
        String token = authHeader.substring(7);
        String username = JwtTokenUtil.getUsername(token);

        boolean created = userService.createProfile(username, profileRequest);
        if (!created) {
            return ResponseEntity.badRequest().body("Profile already exists");
        }

        return ResponseEntity.ok("Profile created successfully");
    }

    @GetMapping("/filter")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<User>> getUsersByRole(@RequestParam String role) {
        List<User> users = userService.getUsersByRole(role);
        return ResponseEntity.ok(users);
    }

    


}
