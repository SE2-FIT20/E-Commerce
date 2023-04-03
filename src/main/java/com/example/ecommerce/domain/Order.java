package com.example.ecommerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Data
@Table(name = "Orders") // Order is a reserved word in SQL
public class Order {
     public enum OrderStatus{
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
    @JsonIgnore
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Store store;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> items;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

//    public static Order createOrder(List<OrderItem> items) {
//        return new Order(null, items, "CREATED");
//    }

}
