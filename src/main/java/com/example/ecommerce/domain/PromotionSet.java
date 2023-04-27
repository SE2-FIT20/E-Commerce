package com.example.ecommerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
    protected List<Promotion> subtractItems(List<? extends Promotion> promotionList, int quantity) {
        List<Promotion> removed = new ArrayList<>();
        Iterator<? extends Promotion> iterator = promotionList.iterator();
        while (iterator.hasNext()) {
            Promotion coupon = iterator.next();
            // only remove unused coupon
            if (!coupon.isUsed()) {
                iterator.remove();
                removed.add(coupon);
                quantity--;
            }
            if (quantity == 0) {
                break;
            }
        }
        // return the removed coupons, since the relationship is bidirectional,
        // the coupons will be removed from the coupon list and removed from the database
        return removed;
    }

    @JsonIgnore
    public abstract Promotion getAnUnUsedItem();
    public String getStatus() {
        if (expiredAt.isBefore(LocalDateTime.now())) {
            return "Expired";
        }
        return "Available";
    }
}
