package com.example.ecommerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@DiscriminatorValue("voucher")
@Data
public class Voucher extends Promotion {

    // no additional attributes or relationships

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private VoucherSet voucherSet;
}