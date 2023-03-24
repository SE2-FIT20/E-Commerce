package com.example.ecommerce.service.service;

import com.example.ecommerce.domain.Order;
import com.example.ecommerce.dto.request.order.CreateOrderRequest;
import com.example.ecommerce.dto.request.order.UpdateOrderRequest;
import com.example.ecommerce.dto.response.Response;
import org.springframework.http.ResponseEntity;

public interface OrderService {

    ResponseEntity<Response> createOrder(CreateOrderRequest request);

    ResponseEntity<Response> deleteOrderById(Long orderId);

    ResponseEntity<Response> updateOrder(UpdateOrderRequest order);

    ResponseEntity<Response> getOrderById(Long orderId);

    ResponseEntity<Response> getAllOrder();

    void save(Order order);
}
