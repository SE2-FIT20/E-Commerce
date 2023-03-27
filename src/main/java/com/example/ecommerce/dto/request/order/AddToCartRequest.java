package com.example.ecommerce.dto.request.order;

import lombok.Data;

@Data
public class AddToCartRequest {
    private OrderItemDTO item;

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