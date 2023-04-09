package com.example.ecommerce.service.service;

import com.example.ecommerce.domain.Customer;
import com.example.ecommerce.domain.Order;
import com.example.ecommerce.dto.request.order.UpdateOrderRequest;
import com.example.ecommerce.dto.response.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public interface OrderService {

//    ResponseEntity<Response> createOrder(AddToCartRequest request);
//
//    ResponseEntity<Response> deleteOrderById(Long orderId);

    ResponseEntity<Response> updateOrder(UpdateOrderRequest order);

    ResponseEntity<Response> getOrderById(Long orderId);

    ResponseEntity<Response> getAllOrder();

    void save(Order order);

    Page<Order> getAllOrdersOfCustomer(Integer pageNumber, Integer elementsPerPage,  Customer customer, String status, String filter, String sortBy, LocalDateTime from, LocalDateTime to);
}
