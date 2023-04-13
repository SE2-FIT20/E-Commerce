package com.example.ecommerce.domain;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@DiscriminatorValue("coupon")
@Data
public class Coupon extends Promotion{

    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;
}
