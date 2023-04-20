package com.example.ecommerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CheckoutRequest {
    private Long deliveryPartnerId;
    private String destinationAddress;
    private String paymentMethod;
}
