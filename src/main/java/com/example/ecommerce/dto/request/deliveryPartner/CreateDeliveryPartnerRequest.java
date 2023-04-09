package com.example.ecommerce.dto.request.deliveryPartner;

import jakarta.persistence.Lob;
import lombok.Data;

@Data
public class CreateDeliveryPartnerRequest {
    private String avatar;
    private String email;
    private String password;
    private String name;
    @Lob
    private String description;
    private Double shippingFee;
}
