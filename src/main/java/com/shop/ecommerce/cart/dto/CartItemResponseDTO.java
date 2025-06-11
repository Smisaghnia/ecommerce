package com.shop.ecommerce.cart.dto;

import java.math.BigDecimal;

public record CartItemResponseDTO(
        Long productId,
        String productName,
        BigDecimal productPrice,
        int quantity
) {}
