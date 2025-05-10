package kg.alatoo.todolist.repositories;

import kg.alatoo.todolist.entities.RefreshToken;
import kg.alatoo.todolist.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByToken(String token);
    void deleteByUser(User user);
}

