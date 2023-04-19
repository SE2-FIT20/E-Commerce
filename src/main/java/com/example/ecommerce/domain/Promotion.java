package com.example.ecommerce.domain;

import com.example.ecommerce.dto.response.CustomerBriefInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double percent;
    @Column(columnDefinition="LONGTEXT")
    private String description;
    private String code;
    private boolean isUsed;
    private LocalDateTime createdAt;
    private LocalDateTime startAt;
    private LocalDateTime expiredAt;
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    private CustomerBriefInfo getCustomer() {
        return new CustomerBriefInfo(customer); // only return necessary information, hide sensitive information
    }
}
