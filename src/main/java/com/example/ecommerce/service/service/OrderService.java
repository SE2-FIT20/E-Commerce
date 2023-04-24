package com.example.ecommerce.service.service;

import com.example.ecommerce.domain.Customer;
import com.example.ecommerce.domain.DeliveryPartner;
import com.example.ecommerce.domain.Order;
import com.example.ecommerce.domain.Store;
import com.example.ecommerce.dto.request.order.UpdateOrderRequest;
import com.example.ecommerce.dto.response.Response;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public interface OrderService {


    Order findOrderById(Long orderId);

    void save(Order order);


    Page<Order> getAll(Example<Order> example, Pageable pageable);

    Page<Order> getAllByDeliveryPartnerAndStatus(DeliveryPartner deliveryPartner, Order.OrderStatus orderStatus, Pageable pageable);

    Page<Order> getAllByDeliveryPartner(DeliveryPartner deliveryPartner, Pageable pageable);

    Map<String, Long> countOrdersByCustomer(Customer customer, LocalDateTime fromDateTime, LocalDateTime toDateTime);

    Page<Order> findAllByStoreAndCreatedAtBetween(Store store, LocalDateTime from, LocalDateTime to, Pageable pageable);

    Page<Order> findAllByStoreAndStatusInAndCreatedAtBetween(Store store, List<Order.OrderStatus> orderStatuses, LocalDateTime from, LocalDateTime to, Pageable pageable);

    Page<Order> findAll(Example<Order> example, Pageable pageable);

    @Transactional
    ResponseEntity<Response> updateOrder(UpdateOrderRequest request);

    void handleWhenOrderIsCancelled(Order order);
    void handleWhenOrderIsDelivered(Order order);

    Map<String, Long> countOrdersByStore(Store store, LocalDateTime fromDateTime, LocalDateTime toDateTime);


    Map<String, Long> countOrdersByDeliveryPartner(DeliveryPartner deliveryPartner, LocalDateTime fromDateTime, LocalDateTime toDateTime);

    Order findByOrderCode(String orderCode);


    Page<Order> findAllByCustomerAndCreatedAtBetween(Customer customer, LocalDateTime from, LocalDateTime to, Pageable pageable);

    Page<Order> findAllByCustomerAndStatusInAndCreatedAtBetween(Customer customer, List<Order.OrderStatus> statuses, LocalDateTime from, LocalDateTime to, Pageable pageable);
}
