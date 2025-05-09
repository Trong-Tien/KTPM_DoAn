package iuh.fit.user_service.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class User {
    @Id
    private String id;

    private String username;
    private String email;
    private String role;

    // Thông tin cá nhân
    private String fullName;
    private String gender;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateOfBirth;
    private String phone;
    private String address;

    // Thông tin tài khoản ngân hàng
    private List<BankAccount> linkedBankAccounts = new ArrayList<>();

}