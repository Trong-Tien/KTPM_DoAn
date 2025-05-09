package iuh.fit.auth_service.service;

import iuh.fit.auth_service.client.UserClient;
import iuh.fit.auth_service.dto.AuthResponse;
import iuh.fit.auth_service.dto.LoginRequest;
import iuh.fit.auth_service.dto.RegisterRequest;
import iuh.fit.auth_service.dto.SyncUserRequest;
import iuh.fit.auth_service.model.User;
import iuh.fit.auth_service.repository.UserRepository;
import iuh.fit.auth_service.security.JwtTokenProvider;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserClient userClient;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setRole(request.getRole() != null ? request.getRole() : "ROLE_USER");

        userRepository.save(user);
        String token = jwtTokenProvider.generateToken(user.getUsername(), user.getRole());

        // Gọi sang user-service bằng FeignClient
        try {
            SyncUserRequest syncRequest = new SyncUserRequest();
            syncRequest.setUsername(user.getUsername());
            syncRequest.setEmail(user.getEmail());
            syncRequest.setRole(user.getRole());

            var response = userClient.syncUser(syncRequest);
            System.out.println("✅ Đồng bộ user thành công: " + response.getBody());
        } catch (FeignException.BadRequest e) {
            System.out.println("⚠️ User đã tồn tại trong user_service: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("❌ Lỗi khi sync user: " + e.getMessage());
            e.printStackTrace();
        }

        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Mật khẩu không đúng");
        }

        String token = jwtTokenProvider.generateToken(user.getUsername(), user.getRole());
        return new AuthResponse(token);
    }
}
