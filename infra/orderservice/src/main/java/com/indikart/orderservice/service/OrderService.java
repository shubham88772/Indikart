package com.indikart.orderservice.service;

import com.indikart.orderservice.dto.request.CreateOrderRequest;
import com.indikart.orderservice.dto.response.OrderResponse;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(CreateOrderRequest request);

    OrderResponse getOrderById(Long id);

    List<OrderResponse> getOrdersByUserId(Long userId);
}
