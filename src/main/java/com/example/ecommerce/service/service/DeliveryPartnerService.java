package com.example.ecommerce.service.service;

import com.example.ecommerce.domain.DeliveryPartner;
import com.example.ecommerce.dto.request.UpdateDeliveryPartnerAccountRequest;
import com.example.ecommerce.dto.request.deliveryPartner.CreateDeliveryPartnerRequest;
import com.example.ecommerce.dto.request.deliveryPartner.UpdateDeliveryPartnerRequest;
import com.example.ecommerce.dto.request.order.UpdateOrderRequest;
import com.example.ecommerce.dto.response.Response;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

public interface DeliveryPartnerService {

     DeliveryPartner findDeliveryPartnerById(Long deliveryPartnerId);
     ResponseEntity<Response> createDeliveryPartner(CreateDeliveryPartnerRequest deliveryPartner);

     ResponseEntity<Response> updateDeliveryPartner(UpdateDeliveryPartnerRequest deliveryPartner);

     ResponseEntity<Response> deleteDeliveryPartnerById(Long deliveryPartnerId);

     ResponseEntity<Response> getDeliveryPartnerById(Long deliveryPartnerId);

     ResponseEntity<Response> getAllDeliveryPartners(Integer pageNumber, Integer elementsPerPage);

     ResponseEntity<Response> getAllOrderByDeliveryPartners(Integer page, Integer elementsPerPage, Long id, String status, String filter, String sortBy, String from, String to);

     ResponseEntity<Response> getOrderById(Long deliveryPartnerId, Long orderId);

     ResponseEntity<Response> updateOrder(Long deliveryPartnerId, UpdateOrderRequest updateRequest);

    ResponseEntity<Response> getAccountInformation(Long id);

     ResponseEntity<Response> updateAccount(Long id, UpdateDeliveryPartnerAccountRequest updateAccountRequest);

     ResponseEntity<Response> countOrders(Long id, LocalDateTime fromDateTime, LocalDateTime toDateTime);

}
