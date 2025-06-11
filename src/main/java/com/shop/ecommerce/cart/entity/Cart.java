package com.shop.ecommerce.cart.entity;

import com.shop.ecommerce.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Cart {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    @Column(name = "total_price", precision = 10, scale = 2)
    private BigDecimal totalPrice = BigDecimal.ZERO;

    /**
     * Setzt den Gesamtpreis des Warenkorbs.
     * @param total Der zu setzende Preis (muss nicht-negativ sein)
     * @throws IllegalArgumentException Wenn der Preis negativ ist
     */
    public void setTotalPrice(BigDecimal total) {
        if (total.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Gesamtpreis darf nicht negativ sein");
        }
        this.totalPrice = total;
    }
    /**
     * Hilfsmethode: Aktualisiert den Gesamtpreis basierend auf den Items
     */
    public void calculateTotalPrice() {
        this.totalPrice = items.stream()
                .map(item -> item.getProduct().getPrice()
                        .multiply(BigDecimal.valueOf(item.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}