package kg.alatoo.todolist.services;

import kg.alatoo.todolist.entities.Role;
import kg.alatoo.todolist.entities.User;
import kg.alatoo.todolist.services.security.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.TestPropertySource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class JwtServiceTest {

    @Autowired
    private JwtService jwtService;

    @Test
    void shouldGenerateAndExtractUsername() {
        User user = new User();
        user.setEmail("test@example.com");
        user.setRole(Role.USER);

        String token = jwtService.generateToken(user);
        assertNotNull(token);
        assertTrue(token.length() > 10);

        String extracted = jwtService.extractUsername(token);
        assertEquals("test@example.com", extracted);
    }

    @Test
    void shouldReturnTrueIfTokenValid() {
        User user = new User();
        user.setEmail("valid@example.com");
        user.setRole(Role.USER);

        String token = jwtService.generateToken(user);

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("valid@example.com");

        boolean valid = jwtService.isTokenValid(token, userDetails);
        assertTrue(valid);
    }

    @Test
    void shouldReturnFalseIfTokenInvalid() {
        User user = new User();
        user.setEmail("admin@example.com");
        user.setRole(Role.ADMIN);

        String token = jwtService.generateToken(user);

        UserDetails fakeUser = mock(UserDetails.class);
        when(fakeUser.getUsername()).thenReturn("hacker@example.com");

        assertFalse(jwtService.isTokenValid(token, fakeUser));
    }
}
