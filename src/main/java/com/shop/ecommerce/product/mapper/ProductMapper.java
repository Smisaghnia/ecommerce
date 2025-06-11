package com.shop.ecommerce.product.mapper;

import com.shop.ecommerce.product.dto.ProductRequestDTO;
import com.shop.ecommerce.product.dto.ProductResponseDTO;
import com.shop.ecommerce.product.entity.Product;

import java.math.BigDecimal;

public class ProductMapper {

    public static Product toEntity(ProductRequestDTO dto) {
        Product product = new Product();
        product.setName(dto.getName());
        product.setDescription(dto.getDescription());

        if (dto.getPrice() != null) {
            product.setPrice(BigDecimal.valueOf(dto.getPrice()));
        } else {
            product.setPrice(null);
        }

        return product;
    }

    public static ProductResponseDTO toDTO(Product product) {
        return new ProductResponseDTO(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice() != null ? product.getPrice().doubleValue() : null
        );
    }
}
