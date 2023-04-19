package com.example.ecommerce.domain;

import com.example.ecommerce.dto.response.StoreBriefInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@DiscriminatorValue("coupon")
@Data
//TODO: transient field for duplicated fields with the set
public class Coupon extends Promotion{

    @Transient
    private Store store;

    // this method for returning store information to client
    private StoreBriefInfo getStore() {
        return couponSet.getStore(); // only return necessary information, hide sensitive information
    }


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private CouponSet couponSet;

}
