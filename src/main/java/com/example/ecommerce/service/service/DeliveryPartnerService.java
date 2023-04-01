package com.example.ecommerce.service.service;

import com.example.ecommerce.domain.DeliveryPartner;

import java.util.Optional;

public interface DeliveryPartnerService {
     DeliveryPartner findDeliveryPartnerById(Long deliveryPartnerId);

}
