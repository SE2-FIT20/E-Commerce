package com.example.ecommerce.dto.request.deliveryPartner;

import lombok.Data;

@Data
public class UpdateDeliveryPartnerRequest {
    private Long deliveryPartnerId;
    private String name;
    //TODO: deliveringFee

}
