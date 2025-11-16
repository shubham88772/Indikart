package com.indikart.orderservice.service.impl;

import com.indikart.orderservice.dto.request.CreateOrderRequest;
import com.indikart.orderservice.dto.request.OrderItemRequest;
import com.indikart.orderservice.dto.response.OrderResponse;
import com.indikart.orderservice.entity.Order;
import com.indikart.orderservice.entity.OrderItem;
import com.indikart.orderservice.entity.OrderStatus;
import com.indikart.orderservice.exception.ResourceNotFoundException;
import com.indikart.orderservice.repository.OrderRepository;
import com.indikart.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Override
    @Transactional
    public OrderResponse createOrder(CreateOrderRequest request) {

        // Total amount calculation
        BigDecimal totalAmount = request.getItems().stream()
                .map(i -> i.getUnitPrice().multiply(BigDecimal.valueOf(i.getQuantity())))
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Create Order entity
        Order order = Order.builder()
                .userId(request.getUserId())
                .totalAmount(totalAmount)
                .status(OrderStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        // Create OrderItems
        List<OrderItem> items = request.getItems().stream()
                .map(reqItem -> OrderItem.builder()
                        .productId(reqItem.getProductId())
                        .quantity(reqItem.getQuantity())
                        .unitPrice(reqItem.getUnitPrice())
                        .order(order)
                        .build())
                .collect(Collectors.toList());

        order.setItems(items);

        Order saved = orderRepository.save(order);

        return modelMapper.map(saved, OrderResponse.class);
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with id " + id));
        return modelMapper.map(order, OrderResponse.class);
    }

    @Override
    public List<OrderResponse> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId)
                .stream()
                .map(order -> modelMapper.map(order, OrderResponse.class))
                .collect(Collectors.toList());
    }
}
