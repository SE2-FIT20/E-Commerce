package com.example.ecommerce.service.impl;

import com.example.ecommerce.domain.DeliveryPartner;
import com.example.ecommerce.repository.DeliveryPartnerRepository;
import com.example.ecommerce.service.service.DeliveryPartnerService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DeliveryPartnerImpl implements DeliveryPartnerService {

    private final DeliveryPartnerRepository deliveryPartnerRepository;
    public DeliveryPartner findDeliveryPartnerById(Long deliveryPartnerId) {
        Optional<DeliveryPartner> deliveryPartnerIdOptional = deliveryPartnerRepository.findById(deliveryPartnerId);
        DeliveryPartner deliveryPartner = null;

        if (deliveryPartnerIdOptional.isPresent()) {
            deliveryPartner =  deliveryPartnerIdOptional.get();
        } else {
            deliveryPartner = DeliveryPartner.builder()
                    .name("Giao hàng nhanh")
                    .description("Giao hàng nhanh trong vòng 1 ngày")
                    .shippingFee(0.0)
                    .build();
            deliveryPartner = deliveryPartnerRepository.save(deliveryPartner);
        }
        return deliveryPartner;
    }


}
