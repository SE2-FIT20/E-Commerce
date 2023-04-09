package com.example.ecommerce.domain;

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
    private String name;
    private double percent;
    @Lob
    private String description;
    private String code;
    private String image;
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Store store;

    private boolean isUsed;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;

    public boolean isGlobal() {
        return store == null;
    }
}
