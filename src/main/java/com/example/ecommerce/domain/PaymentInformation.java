package com.example.ecommerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude = {"customer"})
public class PaymentInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nameOnCard;
    private String cardNumber;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private int cvv;
    private LocalDate expirationDate;
    @Enumerated
    private CardType cardType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Customer customer;

    public enum CardType {
        VISA, MASTERCARD, NAPAS
    }

    public String getCardNumber() {
        String last4Digits = cardNumber.substring(cardNumber.length() - 4);
        return "**** **** **** " + last4Digits;
    }

}
