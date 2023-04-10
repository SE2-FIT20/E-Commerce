package com.example.ecommerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.util.Deque;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@ToString(exclude = {"cart", "orders"}) // to prevent the error of "could not initialize proxy - no Session"
public class Customer extends User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection(fetch = FetchType.EAGER)
    @Cascade({org.hibernate.annotations.CascadeType.ALL})
    private List<String> addresses;

    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL)
    @JsonIgnore
    private Cart cart;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Order> orders;


}
