package com.shop.ecommerce.order.service;

import com.shop.ecommerce.order.dto.OrderRequestDTO;
import com.shop.ecommerce.order.dto.OrderResponseDTO;

public interface OrderService {
    OrderResponseDTO createOrder(OrderRequestDTO orderRequest);
}
