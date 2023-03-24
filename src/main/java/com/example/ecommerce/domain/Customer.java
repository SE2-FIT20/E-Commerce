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
public class Customer extends User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ElementCollection
    private List<String> addresses;
    private String phoneNumber;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> cart;

}
