package com.shop.ecommerce.order.dto;

public class OrderResponseDTO {
    private Long orderId;
    private String message;

    public OrderResponseDTO(Long orderId, String message) {
        this.orderId = orderId;
        this.message = message;
    }

    public Long getOrderId() { return orderId; }
    public String getMessage() { return message; }
}
