package com.example.ecommerce.dto.request.order;

import lombok.Data;

@Data
public class UpdateOrderRequest {
    private Long orderId;
    private String status;
    private String description;

}
