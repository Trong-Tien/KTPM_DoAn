package iuh.fit.user_service.controller;

import iuh.fit.user_service.dto.LinkBankAccountRequest;
import iuh.fit.user_service.model.BankAccount;
import iuh.fit.user_service.model.User;
import iuh.fit.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user/bank-account")
@RequiredArgsConstructor
public class UserBankAccountController {

    private final UserRepository userRepository;

    @PostMapping
    public ResponseEntity<?> linkBankAccount(@RequestParam String username, @RequestBody LinkBankAccountRequest request) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));

        user.getLinkedBankAccounts().add(new BankAccount(request.getBankName(), request.getAccountNumber()));
        userRepository.save(user);

        return ResponseEntity.ok("Liên kết ngân hàng thành công!");
    }

    @GetMapping
    public ResponseEntity<List<BankAccount>> getLinkedBankAccounts(@RequestParam String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));
        return ResponseEntity.ok(user.getLinkedBankAccounts());
    }

    @GetMapping("/check")
    public ResponseEntity<Boolean> hasLinkedBankAccount(@RequestParam String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user với username: " + username));
        return ResponseEntity.ok(!user.getLinkedBankAccounts().isEmpty());
    }
}
