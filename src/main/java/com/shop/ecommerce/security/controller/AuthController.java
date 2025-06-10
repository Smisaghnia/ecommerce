package com.shop.ecommerce.security.controller;

import com.shop.ecommerce.common.dto.RegistrationRequest;
import com.shop.ecommerce.user.entity.User;
import com.shop.ecommerce.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;

    // Konstruktor-Injektion von UserService
    public AuthController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Registrierung eines neuen Benutzers.
     * Nimmt ein JSON-Objekt entgegen mit E-Mail, Passwort, etc.
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegistrationRequest request) {
        try {
            // Registrierung über den Service
            User user = userService.registerUser(
                    request.getEmail(),
                    request.getPassword(),
                    request.getFirstName(),
                    request.getLastName()
            );
            // Erfolgsmeldung zurückgeben
            return ResponseEntity.ok("Benutzer erfolgreich registriert: " + user.getEmail());
        } catch (IllegalArgumentException e) {
            // Fehler z. B. wenn E-Mail schon existiert
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
