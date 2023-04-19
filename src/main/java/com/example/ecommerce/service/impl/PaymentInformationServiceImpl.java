package com.example.ecommerce.service.impl;

import com.example.ecommerce.domain.PaymentInformation;
import com.example.ecommerce.repository.PaymentInformationRepository;
import com.example.ecommerce.service.service.PaymentInformationService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentInformationServiceImpl implements PaymentInformationService {

    private final PaymentInformationRepository paymentInformationRepository;

    @Override
    public void save(PaymentInformation paymentInformation) {
        paymentInformationRepository.save(paymentInformation);
    }

    @Override
    public PaymentInformation findPaymentInformationById(Long paymentInformationId) {
        return paymentInformationRepository
                .findById(paymentInformationId)
                .orElseThrow(() -> new RuntimeException("Payment information not found for id: " + paymentInformationId));
    }

    @Override
    public void deleteByPaymentInformationId(Long paymentInformationId) {
        paymentInformationRepository.deleteById(paymentInformationId);
    }
}
