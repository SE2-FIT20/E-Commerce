package com.example.ecommerce.domain;

import com.example.ecommerce.dto.response.CustomerBriefInfo;
import com.example.ecommerce.dto.response.DeliveryPartnerBriefInformation;
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
        PENDING,
        READY_FOR_DELIVERY,
        DELIVERING,
        DELIVERED,
        CANCELLED_BY_CUSTOMER,
        CANCELLED_BY_STORE,
        DELIVERY_FAILED;

         public static OrderStatus fromString(String status) {
             return switch (status) {
                 case "PENDING" -> PENDING;
                 case "READY_FOR_DELIVERY" -> READY_FOR_DELIVERY;
                 case "DELIVERING" -> DELIVERING;
                 case "DELIVERED" -> DELIVERED;
                 case "CANCELLED_BY_CUSTOMER" -> CANCELLED_BY_CUSTOMER;
                 case "CANCELLED_BY_STORE" -> CANCELLED_BY_STORE;
                 case "DELIVERED_FAILED" -> DELIVERY_FAILED;
                 default -> null;
             };
         }

    }

    public enum PaymentMethod {
        CASH_ON_DELIVERY, ONLINE_PAYMENT;
        public static PaymentMethod fromString(String method) {
            return switch (method.toUpperCase()) {
                case "CASH_ON_DELIVERY" -> CASH_ON_DELIVERY;
                case "ONLINE_PAYMENT" -> ONLINE_PAYMENT;
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

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;
    @ManyToOne(fetch = FetchType.LAZY)
    private DeliveryPartner deliveryPartner;

    private String orderCode;
    private LocalDateTime createdAt;
    private LocalDateTime deliveredAt;
    private String destinationAddress;
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


    public DeliveryPartnerBriefInformation getDeliveryPartner() {
        if (deliveryPartner == null) return null;

        return new DeliveryPartnerBriefInformation(deliveryPartner);
    }
}
