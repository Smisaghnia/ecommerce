package com.shop.ecommerce.cart.repository;

import com.shop.ecommerce.cart.entity.Cart;
import com.shop.ecommerce.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    /**
     * Findet den Warenkorb eines Benutzers
     * @param user Der Benutzer
     * @return Optional<Cart> (leer, falls kein Warenkorb existiert)
     */
    Optional<Cart> findByUser(User user);

    /**
     * Findet den Warenkorb mit allen Items (eager loading)
     * @param cartId Die Warenkorb-ID
     * @return Cart mit geladenen Items
     */
    @Query("SELECT c FROM Cart c LEFT JOIN FETCH c.items WHERE c.id = :cartId")
    Optional<Cart> findByIdWithItems(@Param("cartId") Long cartId);

    /**
     * Prüft, ob ein Benutzer einen Warenkorb hat
     */
    boolean existsByUser(User user);
}