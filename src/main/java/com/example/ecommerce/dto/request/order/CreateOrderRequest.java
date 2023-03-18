package com.example.ecommerce.dto.request.order;

import com.example.ecommerce.domain.OrderItem;
import lombok.Data;

import java.util.List;

@Data
public class CreateOrderRequest {
    private List<OrderItem> items;
    private String status;
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