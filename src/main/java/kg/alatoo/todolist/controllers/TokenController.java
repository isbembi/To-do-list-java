package kg.alatoo.todolist.controllers;

import kg.alatoo.todolist.entities.RefreshToken;
import kg.alatoo.todolist.entities.User;
import kg.alatoo.todolist.repositories.UserRepository;
import kg.alatoo.todolist.services.security.JwtService;
import kg.alatoo.todolist.services.security.RefreshTokenService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class TokenController {

    private final RefreshTokenService refreshTokenService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public TokenController(RefreshTokenService refreshTokenService, JwtService jwtService, UserRepository userRepository) {
        this.refreshTokenService = refreshTokenService;
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody Map<String, String> request) {
        String requestToken = request.get("refreshToken");

        return refreshTokenService.findByToken(requestToken)
                .map(token -> {
                    if (refreshTokenService.isExpired(token)) {
                        refreshTokenService.deleteByUser(token.getUser());
                        return ResponseEntity.badRequest().body("Refresh token expired. Please login again.");
                    }
                    User user = token.getUser();
                    String newAccessToken = jwtService.generateToken(user);
                    return ResponseEntity.ok(Map.of("accessToken", newAccessToken));
                })
                .orElseGet(() -> ResponseEntity.badRequest().body("Invalid refresh token"));
    }
}

