package com.example.ecommerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class PromotionSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private String name;
    private double percent;
    @Column(columnDefinition="LONGTEXT")
    private String description;
    private LocalDateTime startAt;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    private String image;
    @Transient
    private String status;
    @Transient
    public int quantityAvailable;
    public abstract int getQuantityAvailable();
    public abstract void addItems(int quantity);
    public abstract void subtractItems(int quantity);

    @JsonIgnore
    public abstract Promotion getAnUnUsedItem();
    public String getStatus() {
        if (expiredAt.isBefore(LocalDateTime.now())) {
            return "Expired";
        }
        return "Available";
    }
}
