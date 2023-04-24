package com.example.ecommerce.domain;

import com.example.ecommerce.dto.response.StoreBriefInfo;
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

import static com.example.ecommerce.utils.Utils.generateAvatarLink;
import static com.example.ecommerce.utils.Utils.generateRandomString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("coupon_set")
public class CouponSet extends PromotionSet{


    @OneToMany(mappedBy = "couponSet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Coupon> coupons;

    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;

    public int getQuantityAvailable() {
        return coupons.stream().filter(coupon -> coupon.getCustomer() == null).toArray().length;
    }

    @Override
    public void addItems(int quantity) {
        for (int i = 0; i < quantity; i++) {
            Coupon coupon = new Coupon();
            coupon.setCouponSet(this);
            coupon.setCreatedAt(LocalDateTime.now());
            coupons.add(coupon);
        }
    }

    public List<Promotion> subtractCoupons(int quantity) {
        return subtractItems(coupons, quantity);
    }

    @Override
    public Promotion getAnUnUsedItem() {
        return coupons.stream()
                .filter(coupon -> !coupon.isUsed())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No coupon available for this coupon set"));
    }


    public StoreBriefInfo getStore() {
        return new StoreBriefInfo(store);
    }
}
