package com.example.ecommerce.dto.request;

import com.example.ecommerce.domain.PaymentInformation;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.time.LocalDate;

@Data
public class AddPaymentInformationRequest {

    private String nameOnCard;
    private String cardNumber;
    private int cvv;
    private String expirationDate;
}
