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

@AllArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private static final String CHARACTERS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";


    private final OrderRepository orderRepository;

//    @Override
//    public ResponseEntity<Response> createOrder(AddToCartRequest request) {
//
//        List<OrderItem> orderItems = new ArrayList<>();
//        for (AddToCartRequest.OrderItemDTO item : request.getItems()) {
//            OrderItem orderItem = OrderItem.builder()
//                    .product(productService.findProductById(item.getProductId()))
//                    .quantity(item.getQuantity())
//                    .build();
//            orderItems.add(orderItem);
//        }
//
//
//        Order order = Order.builder()
//                .items(orderItems)
//                .status(Order.OrderStatus.PENDING)
//                .build();
//
//        orderRepository.save(order);
//        return ResponseEntity.ok(Response.builder()
//                .status(200)
//                .message("Create order successfully")
//                .data(null)
//                .build());
//    }



//    @Override
//    public ResponseEntity<Response> deleteOrderById(Long orderId) {
//        Order order = findOrderById(orderId);
//        orderRepository.delete(order);
//
//        return ResponseEntity.ok(Response.builder()
//                .status(200)
//                .message("Delete order successfully")
//                .data(null)
//                .build());
//
//    }
    public Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new NotFoundException("Order not found"));
    }

    @Override
    public ResponseEntity<Response> updateOrder(UpdateOrderRequest request) {
        Order order = findOrderById(request.getOrderId());
        Order.OrderStatus status = Order.OrderStatus.valueOf(request.getStatus().toString().toUpperCase());
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
            Order.OrderStatus orderStatus = Order.OrderStatus.valueOf(status.toUpperCase());
            return orderRepository.findAllByCustomerAndStatusAndCreatedAtBetween(customer, orderStatus, from, to, pageable);
        }
    }

    @Override
    public Page<Order> getAll(Example<Order> example, Pageable pageable) {
        return orderRepository.findAll(example, pageable);
    }

    @Override
    public Page<Order> getAllByDeliveryPartnerAndStatus(DeliveryPartner deliveryPartner, Order.OrderStatus orderStatus, Pageable pageable) {

        return orderRepository.findAllByDeliveryPartnerAndStatus(deliveryPartner, orderStatus, pageable);
    }

    @Override
    public Page<Order> getAllByDeliveryPartner(DeliveryPartner deliveryPartner, Pageable pageable) {
        return orderRepository.findAllByDeliveryPartner(deliveryPartner, pageable);
    }

    @Override
    public Map<String, Long> countOrdersByCustomer(Customer customer, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        Map<String, Long> map =  new LinkedHashMap<>();
        long pendingCount = orderRepository.countByCustomerAndStatusAndCreatedAtBetween(customer, Order.OrderStatus.PENDING, fromDateTime, toDateTime);
        long confirmedCount = orderRepository.countByCustomerAndStatusAndCreatedAtBetween(customer, Order.OrderStatus.READY_FOR_DELIVERY, fromDateTime, toDateTime);
        long deliveringCount = orderRepository.countByCustomerAndStatusAndCreatedAtBetween(customer, Order.OrderStatus.DELIVERING, fromDateTime, toDateTime);
        long deliveredCount = orderRepository.countByCustomerAndStatusAndCreatedAtBetween(customer, Order.OrderStatus.DELIVERED, fromDateTime, toDateTime);
        long cancelledCount = orderRepository.countByCustomerAndStatusAndCreatedAtBetween(customer, Order.OrderStatus.CANCELLED, fromDateTime, toDateTime);

        map.put("PENDING", pendingCount);
        map.put("READY_FOR_DELIVER", confirmedCount);
        map.put("DELIVERING", deliveringCount);
        map.put("DELIVERED", deliveredCount);
        map.put("CANCELLED", cancelledCount);


        return map;
    }

    @Override
    public Page<Order> findAllByStoreAndCreatedAtBetween(Store store, LocalDateTime from, LocalDateTime to, Pageable pageable) {
        return orderRepository.findAllByStoreAndCreatedAtBetween(store, from, to, pageable);
    }

    @Override
    public Page<Order> findAllByStoreAndStatusAndCreatedAtBetween(Store store, Order.OrderStatus orderStatus, LocalDateTime from, LocalDateTime to, Pageable pageable) {
        return orderRepository.findAllByStoreAndStatusAndCreatedAtBetween(store, orderStatus, from, to, pageable);
    }

    @Override
    public Page<Order> findAll(Example<Order> example, Pageable pageable) {
        return orderRepository.findAll(example, pageable);
    }


    @Override
    public Map<String, Long> countOrdersByStore(Store store, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        Map<String, Long> map =  new LinkedHashMap<>();
        long pendingCount = orderRepository.countByStoreAndStatusAndCreatedAtBetween(store, Order.OrderStatus.PENDING, fromDateTime, toDateTime);
        long confirmedCount = orderRepository.countByStoreAndStatusAndCreatedAtBetween(store, Order.OrderStatus.READY_FOR_DELIVERY, fromDateTime, toDateTime);
        long deliveringCount = orderRepository.countByStoreAndStatusAndCreatedAtBetween(store, Order.OrderStatus.DELIVERING, fromDateTime, toDateTime);
        long deliveredCount = orderRepository.countByStoreAndStatusAndCreatedAtBetween(store, Order.OrderStatus.DELIVERED, fromDateTime, toDateTime);
        long cancelledCount = orderRepository.countByStoreAndStatusAndCreatedAtBetween(store, Order.OrderStatus.CANCELLED, fromDateTime, toDateTime);

        map.put("PENDING", pendingCount);
        map.put("READY_FOR_DELIVER", confirmedCount);
        map.put("DELIVERING", deliveringCount);
        map.put("DELIVERED", deliveredCount);
        map.put("CANCELLED", cancelledCount);


        return map;
    }


    @Override
    public Map<String, Long> countOrdersByDeliveryPartner(DeliveryPartner deliveryPartner, LocalDateTime fromDateTime, LocalDateTime toDateTime) {
        Map<String, Long> map =  new LinkedHashMap<>();
        long pendingCount = orderRepository.countByDeliveryPartnerAndStatusAndCreatedAtBetween(deliveryPartner, Order.OrderStatus.PENDING, fromDateTime, toDateTime);
        long confirmedCount = orderRepository.countByDeliveryPartnerAndStatusAndCreatedAtBetween(deliveryPartner, Order.OrderStatus.READY_FOR_DELIVERY, fromDateTime, toDateTime);
        long deliveringCount = orderRepository.countByDeliveryPartnerAndStatusAndCreatedAtBetween(deliveryPartner, Order.OrderStatus.DELIVERING, fromDateTime, toDateTime);
        long deliveredCount = orderRepository.countByDeliveryPartnerAndStatusAndCreatedAtBetween(deliveryPartner, Order.OrderStatus.DELIVERED, fromDateTime, toDateTime);
        long cancelledCount = orderRepository.countByDeliveryPartnerAndStatusAndCreatedAtBetween(deliveryPartner, Order.OrderStatus.CANCELLED, fromDateTime, toDateTime);

        map.put("PENDING", pendingCount);
        map.put("READY_FOR_DELIVER", confirmedCount);
        map.put("DELIVERING", deliveringCount);
        map.put("DELIVERED", deliveredCount);
        map.put("CANCELLED", cancelledCount);


        return map;
    }

    @Override
    public Order findByOrderCode(String orderCode) {
        return orderRepository.findByOrderCode(orderCode);
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
