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
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String code;
    private boolean isUsed;
    private LocalDateTime createdAt;
    @Transient
    private String status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Customer customer;
    @Transient
    private String type;
    public CustomerBriefInfo getCustomer() {
        if (customer == null) {
            return null;
        }
        return new CustomerBriefInfo(customer); // only return necessary information, hide sensitive information
    }


    public abstract double getPercent();


    public abstract String getStatus();

    public abstract LocalDateTime getStartAt();

    public abstract LocalDateTime getExpiredAt();

    public abstract String getImage();

    public abstract String getDescription();

//        if (expiredAt.isBefore(LocalDateTime.now())) {
//            return "Expired";
//        }
//        return "Available";
//    }
}
