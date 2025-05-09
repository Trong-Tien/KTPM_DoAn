package iuh.fit.user_service.service;

import iuh.fit.user_service.dto.UpdateProfileRequest;
import iuh.fit.user_service.dto.UserInfoResponse;
import iuh.fit.user_service.model.User;
import iuh.fit.user_service.repository.UserRepository;
import iuh.fit.user_service.security.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserInfoResponse getUserInfoFromToken(String token) {
        String username = JwtTokenUtil.getUsername(token);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return new UserInfoResponse(user.getUsername(), user.getEmail(), user.getRole());
    }

    public boolean saveUserIfNotExists(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            return false;
        }
        userRepository.save(user);
        return true;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public boolean deleteUserByUsername(String username) {
        if (!userRepository.existsByUsername(username)) {
            return false;
        }
        userRepository.deleteByUsername(username);
        return true;
    }

    public void updateProfile(String username, UpdateProfileRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setFullName(request.getFullName());
        user.setGender(request.getGender());
        user.setDateOfBirth(request.getDateOfBirth());
        user.setPhone(request.getPhone());
        user.setAddress(request.getAddress());

        userRepository.save(user);
    }

    public boolean createProfile(String username, UpdateProfileRequest profileRequest) {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null || user.getFullName() != null) {
            return false; // đã có thông tin rồi
        }

        user.setFullName(profileRequest.getFullName());
        user.setGender(profileRequest.getGender());
        user.setDateOfBirth(profileRequest.getDateOfBirth());
        user.setPhone(profileRequest.getPhone());
        user.setAddress(profileRequest.getAddress());

        userRepository.save(user);
        return true;
    }

    public boolean checkUserExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsById(String userId) {
        return userRepository.existsById(userId);
    }

    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }

    public long countAllUsers() {
        return userRepository.count();
    }



}
