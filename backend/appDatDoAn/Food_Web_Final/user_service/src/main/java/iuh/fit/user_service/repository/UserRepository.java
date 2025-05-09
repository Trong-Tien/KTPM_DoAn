package iuh.fit.user_service.repository;

import iuh.fit.user_service.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
    void deleteByUsername(String username);
    List<User> findByRole(String role);


}
