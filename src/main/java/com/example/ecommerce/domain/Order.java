package com.example.ecommerce.domain;

import com.example.ecommerce.dto.response.CustomerBriefInfo;
import com.example.ecommerce.dto.response.CustomerInformation;
import com.example.ecommerce.dto.response.DeliveryPartnerInformation;
import com.example.ecommerce.dto.response.StoreDetailedInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


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
    private String destinationAddress;
    @Transient
    private Long totalPrice;
    @Transient
    private Double shippingFee;

    public Long getTotalPrice() {
        return items.stream()
                .mapToLong(item -> (long) (item.getProduct().getPrice() * item.getQuantity())).sum();
    }

    public Double getShippingFee() {
        if (deliveryPartner == null) return 0.0;
        return deliveryPartner.getShippingFee();
    }
    public CustomerBriefInfo getCustomer() {
        if (customer == null) return null;
        return new CustomerBriefInfo(customer); // only return necessary information, hide sensitive information
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
