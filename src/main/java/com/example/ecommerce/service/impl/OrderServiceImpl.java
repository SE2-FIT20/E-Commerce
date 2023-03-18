package com.example.ecommerce.service.impl;

import com.example.ecommerce.domain.Order;
import com.example.ecommerce.dto.request.order.CreateOrderRequest;
import com.example.ecommerce.dto.request.order.UpdateOrderRequest;
import com.example.ecommerce.dto.response.Response;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.service.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public ResponseEntity<Response> createOrder(CreateOrderRequest request) {

        Order order = Order.builder()
                .items(request.getItems())
                .status(request.getStatus())
                .build();

        orderRepository.save(order);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Create order successfully")
                .data(null)
                .build());
    }

    @Override
    public ResponseEntity<Response> deleteOrderById(Long orderId) {
        Order order = findOrderById(orderId);
        orderRepository.delete(order);

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Delete order successfully")
                .data(null)
                .build());

    }

    private Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));
    }

    @Override
    public ResponseEntity<Response> updateOrder(UpdateOrderRequest request) {
        Order order = findOrderById(request.getOrderId());
        order.setStatus(request.getStatus());

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Update order successfully")
                .data(order)
                .build());
    }


    @Override
    public ResponseEntity<Response> getOrderById(Long orderId) {
        Order order = findOrderById(orderId);
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
