package com.example.ecommerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
//TODO: calculate the average delivery time
public class DeliveryPartner extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    private String description;
    private Double shippingFee;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "deliveryPartner")
    @JsonIgnore
    private List<Order> orders;
}
