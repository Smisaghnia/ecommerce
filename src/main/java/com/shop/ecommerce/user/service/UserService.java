package com.shop.ecommerce.user.service;

import com.shop.ecommerce.user.entity.User;
import com.shop.ecommerce.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service // Markiert diese Klasse als Service-Komponente für Spring (Business-Logik)
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // Konstruktor-Injektion (Spring injiziert automatisch die Abhängigkeiten)
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registriert einen neuen Benutzer mit verschlüsseltem Passwort.
     *
     * @param email      E-Mail-Adresse
     * @param rawPassword Passwort im Klartext
     * @param firstName  Vorname
     * @param lastName   Nachname
     * @return der gespeicherte Benutzer
     * @throws IllegalArgumentException falls E-Mail schon vergeben
     */
    public User registerUser(String email, String rawPassword, String firstName, String lastName) {
        // Prüfung: existiert ein Benutzer mit dieser E-Mail?
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("E-Mail ist bereits vergeben.");
        }

        // Passwort sicher mit z. B. BCrypt verschlüsseln
        String encodedPassword = passwordEncoder.encode(rawPassword);

        // Benutzer-Objekt bauen mit Standardrolle "ROLE_USER"
        User newUser = User.builder()
                .email(email)
                .password(encodedPassword)
                .firstName(firstName)
                .lastName(lastName)
                .roles(Collections.singleton("ROLE_USER"))
                .build();

        // Benutzer in der Datenbank speichern
        return userRepository.save(newUser);
    }
    public void save(User user) {
        userRepository.save(user);
    }
}
