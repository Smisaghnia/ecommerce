package com.shop.ecommerce.order.controller;

import com.shop.ecommerce.order.dto.OrderRequestDTO;
import com.shop.ecommerce.order.dto.OrderResponseDTO;
import com.shop.ecommerce.order.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<OrderResponseDTO> createOrder(@Valid @RequestBody OrderRequestDTO orderRequest) {
        OrderResponseDTO response = orderService.createOrder(orderRequest);
        return ResponseEntity.ok(response);
    }
}
