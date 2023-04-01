package com.example.ecommerce.dto.response;

import com.example.ecommerce.domain.DeliveryPartner;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class DeliveryPartnerInformation {
    private Long id;
    private String name;
    private String description;
    private Double shippingFee;

    public DeliveryPartnerInformation(DeliveryPartner deliveryPartner) {
        this.id = deliveryPartner.getId();
        this.name = deliveryPartner.getName();
        this.description = deliveryPartner.getDescription();
        this.shippingFee = deliveryPartner.getShippingFee();
    }
}