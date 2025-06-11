package com.shop.ecommerce.cart.dto;

public record AddToCartRequest(
        Long productId,
        int quantity
) {}
