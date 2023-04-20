package com.example.ecommerce.service.impl;

import com.example.ecommerce.domain.*;


import com.example.ecommerce.dto.request.order.UpdateOrderRequest;
import com.example.ecommerce.dto.response.Response;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.OrderRepository;
import com.example.ecommerce.service.service.OrderService;
import com.example.ecommerce.service.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

import static com.example.ecommerce.domain.Order.*;
import static com.example.ecommerce.domain.Order.OrderStatus.*;

@AllArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";


    private final OrderRepository orderRepository;
    public Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));
    }

    @Override
    public ResponseEntity<Response> updateOrder(UpdateOrderRequest request) {
        Order order = findOrderById(request.getOrderId());
        OrderStatus status = valueOf(request.getStatus().toString().toUpperCase());
        order.setStatus(status);
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

    @Override
    public void save(Order order) {
        String uuid = generateRandomString(6);
        order.setOrderCode(uuid);
        orderRepository.save(order);
    }

    @Override
    public Page<Order> getAllOrdersOfCustomer(Integer pageNumber, Integer elementsPerPage, Customer customer, String status, String filter, String sortBy, LocalDateTime from, LocalDateTime to) {
        Pageable pageable = PageRequest.of(pageNumber, elementsPerPage, Sort.Direction.valueOf(sortBy.toUpperCase()), filter);

        if (status.equals("ALL")) {
            return orderRepository.findAllByCustomerAndCreatedAtBetween(customer, from, to, pageable);
        } else {
            OrderStatus orderStatus = valueOf(status.toUpperCase());
            return orderRepository.findAllByCustomerAndStatusAndCreatedAtBetween(customer, orderStatus, from, to, pageable);
        }
    }

    @Override
    public Page<Order> getAll(Example<Order> example, Pageable pageable) {
        return orderRepository.findAll(example, pageable);
    }

    @Override
    public Page<Order> getAllByDeliveryPartnerAndStatus(DeliveryPartner deliveryPartner, OrderStatus orderStatus, Pageable pageable) {

        return orderRepository.findAllByDeliveryPartnerAndStatus(deliveryPartner, orderStatus, pageable);
    }

    @Override
    public Page<Order> getAllByDeliveryPartner(DeliveryPartner deliveryPartner, Pageable pageable) {
        return orderRepository.findAllByDeliveryPartner(deliveryPartner, pageable);
    }

    @Override
    public Map<String, Long> countOrdersByCustomer(Customer customer, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        Map<String, Long> map =  new LinkedHashMap<>();
        long pendingCount = orderRepository.countByCustomerAndStatusInAndCreatedAtBetween(customer, List.of(PENDING), fromDateTime, toDateTime);
        long confirmedCount = orderRepository.countByCustomerAndStatusInAndCreatedAtBetween(customer, List.of(READY_FOR_DELIVERY), fromDateTime, toDateTime);
        long deliveringCount = orderRepository.countByCustomerAndStatusInAndCreatedAtBetween(customer, List.of(DELIVERING), fromDateTime, toDateTime);
        long deliveredCount = orderRepository.countByCustomerAndStatusInAndCreatedAtBetween(customer, List.of(DELIVERED), fromDateTime, toDateTime);
        long deliveryFailed = orderRepository.countByCustomerAndStatusInAndCreatedAtBetween(customer, List.of(DELIVERY_FAILED), fromDateTime, toDateTime);
        long cancelledCount = orderRepository.countByCustomerAndStatusInAndCreatedAtBetween(customer, List.of(CANCELLED_BY_CUSTOMER, CANCELLED_BY_STORE), fromDateTime, toDateTime);

        map.put("PENDING", pendingCount);
        map.put("READY_FOR_DELIVER", confirmedCount);
        map.put("DELIVERING", deliveringCount + deliveryFailed);
        map.put("DELIVERED", deliveredCount);
        map.put("CANCELLED", cancelledCount);


        return map;
    }



    @Override
    public Order findByOrderCode(String orderCode) {
        return orderRepository.findByOrderCode(orderCode);
    }

    @Override
    public Page<Order> findAllByStoreAndCreatedAtBetween(Store store, LocalDateTime from, LocalDateTime to, Pageable pageable) {
        return orderRepository.findAllByStoreAndCreatedAtBetween(store, from, to, pageable);
    }

    @Override
    public Page<Order> findAllByStoreAndStatusAndCreatedAtBetween(Store store, OrderStatus orderStatus, LocalDateTime from, LocalDateTime to, Pageable pageable) {
        return orderRepository.findAllByStoreAndStatusAndCreatedAtBetween(store, orderStatus, from, to, pageable);
    }

    @Override
    public Page<Order> findAll(Example<Order> example, Pageable pageable) {
        return orderRepository.findAll(example, pageable);
    }


    @Override
    public Map<String, Long> countOrdersByStore(Store store, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        Map<String, Long> map =  new LinkedHashMap<>();
        long pendingCount = orderRepository.countByStoreAndStatusInAndCreatedAtBetween(store, List.of(PENDING), fromDateTime, toDateTime);
        long confirmedCount = orderRepository.countByStoreAndStatusInAndCreatedAtBetween(store, List.of(READY_FOR_DELIVERY), fromDateTime, toDateTime);
        long deliveringCount = orderRepository.countByStoreAndStatusInAndCreatedAtBetween(store, List.of(DELIVERING), fromDateTime, toDateTime);
        long deliveredCount = orderRepository.countByStoreAndStatusInAndCreatedAtBetween(store, List.of(DELIVERED), fromDateTime, toDateTime);
        long deliveryFailed = orderRepository.countByStoreAndStatusInAndCreatedAtBetween(store, List.of(DELIVERY_FAILED), fromDateTime, toDateTime);
        long cancelledCount = orderRepository.countByStoreAndStatusInAndCreatedAtBetween(store, List.of(CANCELLED_BY_CUSTOMER, CANCELLED_BY_STORE), fromDateTime, toDateTime);

        map.put("PENDING", pendingCount);
        map.put("READY_FOR_DELIVER", confirmedCount);
        map.put("DELIVERING", deliveringCount + deliveryFailed);
        map.put("DELIVERED", deliveredCount);
        map.put("CANCELLED", cancelledCount);


        return map;
    }


    @Override
    public Map<String, Long> countOrdersByDeliveryPartner(DeliveryPartner deliveryPartner, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        Map<String, Long> map =  new LinkedHashMap<>();
        long pendingCount = orderRepository.countByDeliveryPartnerAndStatusInAndCreatedAtBetween(deliveryPartner, List.of(PENDING), fromDateTime, toDateTime);
        long confirmedCount = orderRepository.countByDeliveryPartnerAndStatusInAndCreatedAtBetween(deliveryPartner, List.of(READY_FOR_DELIVERY), fromDateTime, toDateTime);
        long deliveringCount = orderRepository.countByDeliveryPartnerAndStatusInAndCreatedAtBetween(deliveryPartner, List.of(DELIVERING), fromDateTime, toDateTime);
        long deliveredCount = orderRepository.countByDeliveryPartnerAndStatusInAndCreatedAtBetween(deliveryPartner, List.of(DELIVERED), fromDateTime, toDateTime);
        long deliveryFailed = orderRepository.countByDeliveryPartnerAndStatusInAndCreatedAtBetween(deliveryPartner, List.of(DELIVERY_FAILED), fromDateTime, toDateTime);

        map.put("PENDING", pendingCount);
        map.put("READY_FOR_DELIVER", confirmedCount);
        map.put("DELIVERING", deliveringCount);
        map.put("DELIVERED", deliveredCount);
        map.put("DELIVERY_FAILED", deliveryFailed);


        return map;
    }
    private static String generateRandomString(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(CHARACTERS.length());
            char randomChar = CHARACTERS.charAt(randomIndex);
            sb.append(randomChar);
        }
        return sb.toString();
    }






}
