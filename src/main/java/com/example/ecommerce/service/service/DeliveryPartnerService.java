package com.example.ecommerce.service.service;

import com.example.ecommerce.domain.DeliveryPartner;
import com.example.ecommerce.dto.request.deliveryPartner.CreateDeliveryPartnerRequest;
import com.example.ecommerce.dto.request.deliveryPartner.UpdateDeliveryPartnerRequest;
import com.example.ecommerce.dto.response.Response;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

public interface DeliveryPartnerService {

     DeliveryPartner findDeliveryPartnerById(Long deliveryPartnerId);
     ResponseEntity<Response> createDeliveryPartner(CreateDeliveryPartnerRequest deliveryPartner);

     ResponseEntity<Response> updateDeliveryPartner(UpdateDeliveryPartnerRequest deliveryPartner);

     ResponseEntity<Response> deleteDeliveryPartnerById(Long deliveryPartnerId);

     ResponseEntity<Response> getDeliveryPartnerById(Long deliveryPartnerId);

     ResponseEntity<Response> getAllDeliveryPartners(Integer pageNumber, Integer elementsPerPage);
}
