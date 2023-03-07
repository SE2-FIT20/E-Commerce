package com.example.ecommerce.domain;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Order {

    private Long id;
    private List<OrderItem> items;
    private String status;

    public static Order createOrder(List<OrderItem> items) {
        return new Order(null, items, "CREATED");
    }

}
