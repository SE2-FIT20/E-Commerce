package com.example.ecommerce.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Store extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;
    private String address;
    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> inventory;

    @OneToMany(mappedBy = "store", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;
}
