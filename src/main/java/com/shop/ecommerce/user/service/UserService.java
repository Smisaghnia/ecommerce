// Datei: com.shop.ecommerce.user.service.UserService.java
package com.shop.ecommerce.user.service;

import com.shop.ecommerce.security.jwt.JwtService;
import com.shop.ecommerce.user.entity.User;
import com.shop.ecommerce.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.shop.ecommerce.user.enums.Role;


import java.util.Set;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    /**
     * Registrierung mit Standardrolle ROLE_USER
     */
    public User registerUsera(String email, String rawPassword, String firstName, String lastName) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("E-Mail ist bereits vergeben.");
        }

        String encodedPassword = passwordEncoder.encode(rawPassword);

        User user = User.builder()
                .email(email)
                .password(encodedPassword)
                .firstName(firstName)
                .lastName(lastName)
                //.roles(Set.of(Role.ROLE_USER)) // ENUM verwenden!
                .build();

        return userRepository.save(user);
    }
    public User registerUser(String email, String password, String firstName, String lastName) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setRoles(Set.of(Role.KUNDE, Role.MITARBEITER));



        return userRepository.save(user); // gibt den gespeicherten User zurück
    }



    /**
     * Registrierung mit zusätzlichen Rollen – z. B. Admin
     */
    public User registerAdmin(String email, String password, String firstName, String lastName) {
        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("E-Mail ist bereits vergeben.");
        }

        String encodedPassword = passwordEncoder.encode(password);

        User user = User.builder()
                .email(email)
                .password(encodedPassword)
                .firstName(firstName)
                .lastName(lastName)
                .roles(Set.of(Role.ADMIN))
                .build();

        return userRepository.save(user);
    }

    public void save(User user) {
        userRepository.save(user);
    }

    /**
     * Spring Security benötigt diese Methode zur Authentifizierung.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Benutzer nicht gefunden"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getEmail())
                .password(user.getPassword())
                .authorities(user.getRoles().stream().map(Enum::name).toArray(String[]::new))
                .build();
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Benutzer nicht gefunden mit E-Mail: " + email));
    }
    @Bean
    public UserDetailsService userDetailsService(UserService userService) {
        return userService;
    }
}
