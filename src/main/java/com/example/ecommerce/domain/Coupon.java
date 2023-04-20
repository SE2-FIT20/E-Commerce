package com.example.ecommerce.domain;

import com.example.ecommerce.dto.response.StoreBriefInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("coupon")
@Data
public class Coupon extends Promotion{

    @Transient
    private Store store;

    // this method for returning store information to client
    public StoreBriefInfo getStore() {
        return couponSet.getStore(); // only return necessary information, hide sensitive information
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private CouponSet couponSet;

    @Override
    public double getPercent() {
        return couponSet.getPercent();
    }

    @Override
    public String getStatus() {
        return couponSet.getStatus();
    }

    @Override
    public LocalDateTime getStartAt() {
        return couponSet.getStartAt();
    }

    @Override
    public LocalDateTime getExpiredAt() {
        return couponSet.getExpiredAt();
    }

    @Override
    public String getImage() {
        return couponSet.getImage();
    }

    @Override
    public String getDescription() {
        return couponSet.getDescription();
    }
}
