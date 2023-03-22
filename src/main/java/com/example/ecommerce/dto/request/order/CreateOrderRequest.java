package com.example.ecommerce.dto.request.order;

import com.example.ecommerce.domain.OrderItem;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {
    private List<OrderItemDTO> items;

    @Data
    public static class OrderItemDTO {
        private Long productId;
        private Integer quantity;
    }
}
/*
*
* {
	"items": [
		{
			"productId": 1,
			"quantity": 20
		},
		{
			"productId": 1,
			"quantity": 20
		},
	],
	"status": "DELIVERED
}
* */