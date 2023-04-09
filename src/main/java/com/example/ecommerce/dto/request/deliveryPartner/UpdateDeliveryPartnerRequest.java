package com.example.ecommerce.dto.request.deliveryPartner;

import jakarta.persistence.Lob;
import lombok.Data;

@Data
public class UpdateDeliveryPartnerRequest {
    private Long deliveryPartnerId;
    private String name;
    private String description;
    private Double shippingFee;
}
