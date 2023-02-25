package com.example.ecommerce.dto.request.paymentOption;

import lombok.Data;

@Data
public class UpdatePaymentOption {
    private Long paymentOptionId;
    private String name;
}
