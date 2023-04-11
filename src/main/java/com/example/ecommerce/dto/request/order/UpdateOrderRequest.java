package com.example.ecommerce.dto.request.order;

import com.example.ecommerce.domain.Order;
import lombok.Data;

@Data
public class UpdateOrderRequest {
    private Long orderId;
    private Order.OrderStatus status;
    private String description;

}
