package com.shop.ecommerce.product.dto;

public class ProductRequestDTO {
    private String name;
    private String description;
    private Double price;

    public ProductRequestDTO() {}

    public ProductRequestDTO(String name, String description, Double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    // Getter & Setter
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
}
