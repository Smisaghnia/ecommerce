package com.shop.ecommerce.cart.controller;

import com.shop.ecommerce.cart.dto.AddToCartRequest;
import com.shop.ecommerce.cart.dto.CartItemResponseDTO;
import com.shop.ecommerce.cart.dto.CartResponseDTO;
import com.shop.ecommerce.cart.entity.Cart;
import com.shop.ecommerce.cart.entity.CartItem;
import com.shop.ecommerce.cart.service.CartService;
import com.shop.ecommerce.product.entity.Product;
import com.shop.ecommerce.user.entity.User;
import com.shop.ecommerce.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;



import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<CartResponseDTO> getCart(@AuthenticationPrincipal UserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        User user = userService.findByEmail(userDetails.getUsername());
        Cart cart = cartService.getOrCreateCart(user);
        return ResponseEntity.ok(toDTO(cart));
    }

    @PostMapping("/add")
    public ResponseEntity<CartResponseDTO> addToCart(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody AddToCartRequest request
    ) {
        User user = userService.findByEmail(userDetails.getUsername());
        Cart cart = cartService.addToCart(user, request.productId(), request.quantity());
        return ResponseEntity.ok(toDTO(cart));
    }

    @PutMapping("/update")
    public ResponseEntity<CartResponseDTO> updateCartItem(
            @AuthenticationPrincipal UserDetails userDetails,
            @RequestBody AddToCartRequest request
    ) {
        User user = userService.findByEmail(userDetails.getUsername());
        Cart cart = cartService.updateCartItem(user, request.productId(), request.quantity());
        return ResponseEntity.ok(toDTO(cart));
    }

    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<CartResponseDTO> removeFromCart(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable Long productId
    ) {
        User user = userService.findByEmail(userDetails.getUsername());
        Cart cart = cartService.removeFromCart(user, productId);
        return ResponseEntity.ok(toDTO(cart));
    }

    private CartResponseDTO toDTO(Cart cart) {
        List<CartItemResponseDTO> items = cart.getItems().stream().map(this::toItemDTO).toList();
        return new CartResponseDTO(items, cart.getTotalPrice());
    }

    private CartItemResponseDTO toItemDTO(CartItem item) {
        Product product = item.getProduct();
        return new CartItemResponseDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                item.getQuantity()
        );
    }
}
