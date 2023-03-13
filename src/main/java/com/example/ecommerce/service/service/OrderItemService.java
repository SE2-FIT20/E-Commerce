package com.example.ecommerce.service.service;

import com.example.ecommerce.domain.OrderItem;
import com.example.ecommerce.dto.response.Response;
import org.springframework.http.ResponseEntity;

public interface OrderItemService {

    ResponseEntity<Response> addOrderItem(OrderItem orderItem);

    ResponseEntity<Response> deleteOrderItem(Long orderItemId);
    ResponseEntity<Response> updateOrderItem(Long orderItemId, OrderItem orderItem);
    ResponseEntity<Response> getOrderItemById(Long orderItemId);
}
