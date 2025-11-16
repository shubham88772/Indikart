package com.indikart.orderservice.controller;

import com.indikart.orderservice.dto.response.ApiResponse;
import com.indikart.orderservice.dto.request.CreateOrderRequest;
import com.indikart.orderservice.dto.response.OrderResponse;
import com.indikart.orderservice.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/orders")
@Tag(name = "Order Service")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    @Operation(summary = "Create an order")
    public ResponseEntity<ApiResponse<OrderResponse>> create(@Valid @RequestBody CreateOrderRequest request) {
        return ResponseEntity.ok(new ApiResponse<>(orderService.createOrder(request)));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get order by ID")
    public ResponseEntity<ApiResponse<OrderResponse>> getById(@PathVariable Long id) {
        return ResponseEntity.ok(new ApiResponse<>(orderService.getOrderById(id)));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Get all orders for a user")
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(new ApiResponse<>(orderService.getOrdersByUserId(userId)));
    }
}
