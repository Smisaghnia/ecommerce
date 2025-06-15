package com.shop.ecommerce.product.dto;

import java.math.BigDecimal;

public class ProductResponseDTO {
    private Long id;
    private String name;
    private String description;
    private Double price;
    private String categoryName;

    private String imageFile;

    public ProductResponseDTO() {}

    public ProductResponseDTO(Long id, String name, String description, Double price,String imageFile) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.imageFile = imageFile;
    }

    // Getter & Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
    public String getCategoryName() {
        return categoryName;
    }
    public String getImageFile() {
        return imageFile;
    }

    public void setImageFile(String imageFile) {
        this.imageFile = imageFile;
    }
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
