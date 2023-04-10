package com.example.ecommerce.repository;

import com.example.ecommerce.domain.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findAllByStoreAndCreatedAtBetween(Store store, LocalDateTime from, LocalDateTime to, Pageable pageable);

    Page<Order> findAllByStoreAndStatusAndCreatedAtBetween(Store store, Order.OrderStatus status, LocalDateTime from, LocalDateTime to, Pageable pageable);

    Page<Order> findAllByCustomerAndCreatedAtBetween(Customer customer, LocalDateTime from, LocalDateTime to, Pageable pageable);

    Page<Order> findAllByCustomerAndStatusAndCreatedAtBetween(Customer customer, Order.OrderStatus orderStatus, LocalDateTime from, LocalDateTime to, Pageable pageable);

    Order findByOrderCode(String orderCode);

    Page<Order> findAllByDeliveryPartnerAndStatus(DeliveryPartner deliveryPartner, Order.OrderStatus orderStatus, Pageable pageable);

    Page<Order> findAllByDeliveryPartner(DeliveryPartner deliveryPartner, Pageable pageable);
}
