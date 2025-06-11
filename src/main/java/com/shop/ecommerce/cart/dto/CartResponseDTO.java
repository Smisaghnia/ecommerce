package com.shop.ecommerce.cart.dto;

import java.math.BigDecimal;
import java.util.List;

public record CartResponseDTO(
        List<CartItemResponseDTO> items,
        BigDecimal totalPrice
) {}
