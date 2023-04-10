package com.example.ecommerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateDeliveryPartnerAccountRequest {
    private String description;
    private String avatar;
    private Double shippingFee;
}