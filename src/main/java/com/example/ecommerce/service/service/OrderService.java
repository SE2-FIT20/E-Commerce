package com.example.ecommerce.service.service;

import com.example.ecommerce.domain.Customer;
import com.example.ecommerce.domain.DeliveryPartner;
import com.example.ecommerce.domain.Order;
import com.example.ecommerce.dto.request.order.UpdateOrderRequest;
import com.example.ecommerce.dto.response.Response;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public interface OrderService {

//    ResponseEntity<Response> createOrder(AddToCartRequest request);
//
//    ResponseEntity<Response> deleteOrderById(Long orderId);

    ResponseEntity<Response> updateOrder(UpdateOrderRequest order);

    ResponseEntity<Response> getOrderById(Long orderId);

    ResponseEntity<Response> getAllOrder();

    Order findOrderById(Long orderId);
    void save(Order order);

    Page<Order> getAllOrdersOfCustomer(Integer pageNumber, Integer elementsPerPage,  Customer customer, String status, String filter, String sortBy, LocalDateTime from, LocalDateTime to);

    Page<Order> getAll(Example<Order> example, Pageable pageable);

    Page<Order> getAllByDeliveryPartnerAndStatus(DeliveryPartner deliveryPartner, Order.OrderStatus orderStatus, Pageable pageable);

    Page<Order> getAllByDeliveryPartner(DeliveryPartner deliveryPartner, Pageable pageable);
}
