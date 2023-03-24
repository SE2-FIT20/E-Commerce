package com.example.ecommerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private double percent;
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Store store;


    public boolean isGlobal() {
        return store == null;
    }
}
