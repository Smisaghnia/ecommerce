package com.shop.ecommerce.product.entity;

import com.shop.ecommerce.category.entity.Category;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    @Column(nullable = false)
    private BigDecimal price;

    private int stock;

    @ManyToOne
    @JoinColumn(name = "category_id") // Optional, besser für DB-Lesbarkeit
    private Category category;
}
