package kg.alatoo.todolist.repositories;

import kg.alatoo.todolist.entities.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User(null, "Alice", "alice@example.com", null, "test123");
        user = userRepository.save(user);
    }

    @Test
    void testFindById_Success() {
        Optional<User> foundUser = userRepository.findById(user.getId());

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getName()).isEqualTo("Alice");
    }

    @Test
    void testFindById_NotFound() {
        Optional<User> foundUser = userRepository.findById(999L);

        assertThat(foundUser).isEmpty();
    }

    @Test
    void testFindByEmail_Success() {
        Optional<User> foundUser = userRepository.findByEmail("alice@example.com");

        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getName()).isEqualTo("Alice");
    }

    @Test
    void testFindByEmail_NotFound() {
        Optional<User> foundUser = userRepository.findByEmail("notfound@example.com");

        assertThat(foundUser).isEmpty();
    }

    @Test
    void testDeleteUser() {
        userRepository.delete(user);

        Optional<User> foundUser = userRepository.findById(user.getId());
        assertThat(foundUser).isEmpty();
    }
}
