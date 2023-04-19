package com.example.ecommerce.service.service;

import com.example.ecommerce.domain.PaymentInformation;

public interface PaymentInformationService {
    void save(PaymentInformation paymentInformation);


    PaymentInformation findPaymentInformationById(Long paymentInformationId);

    void deleteByPaymentInformationId(Long paymentInformationId);
}
