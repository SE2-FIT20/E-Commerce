package com.example.ecommerce.domain;

import com.example.ecommerce.dto.response.CustomerInformation;
import com.example.ecommerce.dto.response.DeliveryPartnerInformation;
import com.example.ecommerce.dto.response.StoreDetailedInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
@Table(name = "Orders") // Order is a reserved word in SQL
public class Order {
    public enum OrderStatus {
        PENDING, READY_FOR_DELIVERY, DELIVERING, DELIVERED, CANCELLED;

         public static OrderStatus fromString(String status) {
             return switch (status) {
                 case "PENDING" -> PENDING;
                 case "READY_FOR_DELIVERY" -> READY_FOR_DELIVERY;
                 case "DELIVERING" -> DELIVERING;
                 case "DELIVERED" -> DELIVERED;
                 case "CANCELLED" -> CANCELLED;
                 default -> null;
             };
         }

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Store store;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> items;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    private DeliveryPartner deliveryPartner;

    private String orderCode;
    private LocalDateTime createdAt;
    private LocalDateTime deliveredAt;

    @Transient
    private Long totalPrice;

    public Long getTotalPrice() {
        return items.stream()
                .mapToLong(item -> (long) (item.getProduct().getPrice() * item.getQuantity())).sum();
    }

    public CustomerInformation getCustomer() {
        if (customer == null) return null;
        return new CustomerInformation(customer);
    }

    public StoreDetailedInfo getStore() {
        if (store == null) return null;
        return new StoreDetailedInfo(store);
    }


    public DeliveryPartnerInformation getDeliveryPartner() {
        if (deliveryPartner == null) return null;

        return new DeliveryPartnerInformation(deliveryPartner);
    }
}
