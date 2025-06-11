package com.shop.ecommerce.cart.service;

import com.shop.ecommerce.cart.entity.Cart;
import com.shop.ecommerce.cart.entity.CartItem;
import com.shop.ecommerce.cart.exception.CartNotFoundException;
import com.shop.ecommerce.cart.repository.CartRepository;
import com.shop.ecommerce.product.entity.Product;
import com.shop.ecommerce.product.service.ProductService;
import com.shop.ecommerce.user.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductService productService;

    /**
     * Holt oder erstellt einen Warenkorb für den Benutzer
     */
    @Transactional
    public Cart getOrCreateCart(User user) {
        return cartRepository.findByUser(user)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUser(user);
                    return cartRepository.save(newCart);
                });
    }

    /**
     * Fügt ein Produkt zum Warenkorb hinzu (oder erhöht die Menge)
     */
    @Transactional
    public Cart addToCart(User user, Long productId, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Menge muss größer als 0 sein");
        }

        Product product = productService.getProductById(productId);
        Cart cart = getOrCreateCart(user);

        Optional<CartItem> existingItem = findCartItemForProduct(cart, product);

        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            newItem.setCart(cart);
            cart.getItems().add(newItem);
        }

        updateTotalPrice(cart);
        return cartRepository.save(cart);
    }

    /**
     * Aktualisiert die Menge eines Produkts im Warenkorb
     */
    @Transactional
    public Cart updateCartItem(User user, Long productId, int newQuantity) {
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Menge muss größer als 0 sein");
        }

        Cart cart = getOrCreateCart(user);
        Product product = productService.getProductById(productId);

        findCartItemForProduct(cart, product).ifPresent(item -> {
            item.setQuantity(newQuantity);
            updateTotalPrice(cart);
            cartRepository.save(cart);
        });

        return cart;
    }

    /**
     * Entfernt ein Produkt aus dem Warenkorb
     */
    @Transactional
    public Cart removeFromCart(User user, Long productId) {
        Cart cart = getOrCreateCart(user);
        cart.getItems().removeIf(item ->
                item.getProduct().getId().equals(productId));
        updateTotalPrice(cart);
        return cartRepository.save(cart);
    }

    /**
     * Aktualisiert den Gesamtpreis basierend auf der Cart-Entity-Methode
     */
    private void updateTotalPrice(Cart cart) {
        cart.calculateTotalPrice(); // zentrale Berechnung in der Entity
    }

    /**
     * Findet ein CartItem für ein bestimmtes Produkt
     */
    private Optional<CartItem> findCartItemForProduct(Cart cart, Product product) {
        return cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst();
    }

    /**
     * Gibt einen Warenkorb mit geladenen Items zurück
     */
    public Cart getCartWithItems(Long cartId) {
        return cartRepository.findByIdWithItems(cartId)
                .orElseThrow(() -> new CartNotFoundException("Warenkorb nicht gefunden"));
    }
}
