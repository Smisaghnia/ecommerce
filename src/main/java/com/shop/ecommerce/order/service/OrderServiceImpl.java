package com.shop.ecommerce.order.service;

import com.shop.ecommerce.order.dto.OrderRequestDTO;
import com.shop.ecommerce.order.dto.OrderResponseDTO;

import com.shop.ecommerce.order.entity.Order;
import com.shop.ecommerce.order.entity.OrderItem;
import com.shop.ecommerce.order.repository.OrderRepository;
import com.shop.ecommerce.product.repository.ProductRepository;
import com.shop.ecommerce.product.entity.Product;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;

    public OrderServiceImpl(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    @Override
    public OrderResponseDTO createOrder(OrderRequestDTO orderRequest) {
        Order order = new Order();
        order.setCustomerName(orderRequest.getName());
        order.setShippingAddress(orderRequest.getAddress());
        order.setPaymentMethod(orderRequest.getPaymentMethod());

        List<OrderItem> orderItems = orderRequest.getItems().stream().map(itemDTO -> {
            Product product = productRepository.findById(itemDTO.getProductId())
                    .orElseThrow(() -> new RuntimeException("Produkt nicht gefunden: " + itemDTO.getProductId()));
            OrderItem item = new OrderItem();
            item.setProductName(product.getName()); // nur den Namen speichern
            item.setPrice(product.getPrice());      // Preis aus Produkt übernehmen
            item.setQuantity(itemDTO.getQuantity());
            item.setOrder(order);

            return item;
        }).collect(Collectors.toList());

        order.setItems(orderItems);
        Order savedOrder = orderRepository.save(order);

        return new OrderResponseDTO(savedOrder.getId(), "Bestellung erfolgreich gespeichert.");
    }
}
