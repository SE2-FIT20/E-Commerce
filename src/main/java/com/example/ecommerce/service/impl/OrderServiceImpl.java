package com.example.ecommerce.service.impl;

import com.example.ecommerce.domain.Order;
import com.example.ecommerce.dto.request.order.UpdateOrderRequest;
import com.example.ecommerce.dto.response.Response;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.service.service.OrderService;
import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.List;

public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Override
    public ResponseEntity<Response> createOrder(Order order) {
        orderRepository.save(order);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Create order successfully")
                .data(order)
                .build());
    }

    @Override
    public ResponseEntity<Response> deleteOrderById(Long orderId) {
        orderRepository.deleteById(orderId);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Delete order successfully")
                        .data(orderId)
                .build());

    }


    @Override
    public ResponseEntity<Response> updateOrder(Long orderId, UpdateOrderRequest order) {
        Order updateOrder = orderRepository.getById(orderId);
        updateOrder.setItems(order.getItems());
        updateOrder.setStatus(order.getStatus());
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Update order successfully")
                .data(updateOrder)
                .build());

    }

    @Override
    public ResponseEntity<Response> getOrderById(Long orderId) {
        Order order = orderRepository.getById(orderId);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get order successfully")
                .data(order)
                .build());
    }

    @Override
    public ResponseEntity<Response> getAllOrder() {
        List<Order> allOrder = orderRepository.findAll();
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get all order successfully")
                .data(allOrder)
                .build());
    }
}
