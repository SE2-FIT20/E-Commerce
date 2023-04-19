package com.example.ecommerce.dto.request;

import lombok.Data;

@Data
public class TopUpBalanceRequest {
    private Long paymentInformationId;
    private Double amount;
}
