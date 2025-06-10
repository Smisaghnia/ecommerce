package com.shop.ecommerce.user.repository;

import com.shop.ecommerce.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository // Markiert diese Schnittstelle als Spring-Bean für Datenzugriff
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Findet einen Benutzer anhand der E-Mail-Adresse.
     * Wird z. B. für Login oder Registrierung verwendet.
     */
    Optional<User> findByEmail(String email);

    /**
     * Prüft, ob ein Benutzer mit dieser E-Mail bereits existiert.
     * Nützlich zur Validierung bei Registrierung.
     */
    boolean existsByEmail(String email);
}
