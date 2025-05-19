package kg.alatoo.todolist.controllers;

import kg.alatoo.todolist.dto.AuthRequest;
import kg.alatoo.todolist.dto.AuthResponse;
import kg.alatoo.todolist.dto.RegisterRequest;
import kg.alatoo.todolist.services.security.AuthenticationService;
import kg.alatoo.todolist.validation.OnRegisterValidation;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@Validated
public class AuthController {

    private final AuthenticationService authService;

    public AuthController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody @Validated(OnRegisterValidation.class) RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody @Valid AuthRequest request) {
        return ResponseEntity.ok(authService.authenticate(request));
    }
}
