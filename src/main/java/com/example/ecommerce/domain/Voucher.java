package com.example.ecommerce.domain;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("voucher")
public class Voucher extends Promotion {

    // no additional attributes or relationships

}