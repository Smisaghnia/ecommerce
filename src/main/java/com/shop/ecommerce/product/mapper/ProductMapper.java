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
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setDescription(product.getDescription());
        dto.setPrice(product.getPrice() != null ? product.getPrice().doubleValue() : null);

        // 🆕 Kategorie-Name setzen, wenn vorhanden
        if (product.getCategory() != null) {
            dto.setCategoryName(product.getCategory().getName());
        }
        dto.setImageFile(product.getImageFile());

        return dto;
    }
}
