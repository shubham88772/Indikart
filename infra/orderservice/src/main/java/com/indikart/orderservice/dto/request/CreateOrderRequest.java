package com.indikart.orderservice.dto.request;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {

    @NotNull(message = "userId is required")
    private Long userId;

    @NotEmpty(message = "Order must contain at least one item")
    private List<OrderItemRequest> items;
}
