package com.shop.ecommerce.security.controller;

import com.shop.ecommerce.common.dto.RegistrationRequest;
import com.shop.ecommerce.security.dto.JwtAuthenticationResponse;
import com.shop.ecommerce.security.dto.LoginRequest;
import com.shop.ecommerce.security.jwt.JwtService;
import com.shop.ecommerce.user.entity.User;
import com.shop.ecommerce.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    /**
     * Konstruktor für Dependency Injection.
     * Wichtig: Nur EIN Konstruktor wird benötigt, da alle Abhängigkeiten hier zentral injiziert werden.
     */
    public AuthController(
            UserService userService,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    /**
     * Registriert einen neuen Benutzer.
     * @param request DTO mit Registrierungsdaten (Email, Passwort, Vorname, Nachname)
     * @return Erfolgsmeldung oder Fehler
     */
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody RegistrationRequest request) {
        try {
            User user = userService.registerUser(
                    request.getEmail(),
                    request.getPassword(),
                    request.getFirstName(),
                    request.getLastName()
            );

            // Token generieren
            UserDetails userDetails = userService.loadUserByUsername(user.getEmail());
            String token = jwtService.generateToken(userDetails);

            // JSON-Antwort mit Token
            return ResponseEntity.ok(Map.of("token", token));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }



    /**
     * Authentifiziert einen Benutzer und gibt ein JWT-Token zurück.
     * @param request DTO mit Login-Daten (Email und Passwort)
     * @return JWT-Token oder Fehlermeldung
     */
    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody LoginRequest request) {
        try {
            // 1. Benutzer aus der Datenbank laden
            UserDetails userDetails = userService.loadUserByUsername(request.getEmail());

            // 2. Passwort validieren
            if (!passwordEncoder.matches(request.getPassword(), userDetails.getPassword())) {
                throw new BadCredentialsException("Ungültige Anmeldedaten");
            }

            // 3. Token generieren
            String token = jwtService.generateToken(userDetails);
            return ResponseEntity.ok(token);

        } catch (BadCredentialsException e) {
            return ResponseEntity.badRequest().body("Ungültige Anmeldedaten");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Serverfehler beim Login");
        }
    }
}