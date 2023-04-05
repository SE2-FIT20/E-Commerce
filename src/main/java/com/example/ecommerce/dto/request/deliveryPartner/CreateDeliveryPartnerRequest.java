package com.example.ecommerce.dto.request.deliveryPartner;

import lombok.Data;

@Data
public class CreateDeliveryPartnerRequest {
    private String name;
    private Double deliveringFee;
}
