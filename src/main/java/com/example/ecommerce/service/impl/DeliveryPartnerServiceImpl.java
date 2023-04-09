package com.example.ecommerce.service.impl;

import com.example.ecommerce.domain.DeliveryPartner;
import com.example.ecommerce.dto.request.deliveryPartner.CreateDeliveryPartnerRequest;
import com.example.ecommerce.dto.request.deliveryPartner.UpdateDeliveryPartnerRequest;
import com.example.ecommerce.dto.response.DeliveryPartnerInformation;
import com.example.ecommerce.dto.response.PageResponse;
import com.example.ecommerce.dto.response.Response;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.DeliveryPartnerRepository;
import com.example.ecommerce.service.service.DeliveryPartnerService;
import com.example.ecommerce.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class DeliveryPartnerServiceImpl implements DeliveryPartnerService {

    private final DeliveryPartnerRepository deliveryPartnerRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public DeliveryPartner findDeliveryPartnerById(Long deliveryPartnerId) {
        return deliveryPartnerRepository.findById(deliveryPartnerId)
                .orElseThrow(() -> new NotFoundException("Delivery partner not found with id: " + deliveryPartnerId));
    }

    @Override
    public ResponseEntity<Response> createDeliveryPartner(CreateDeliveryPartnerRequest request) {

        DeliveryPartner deliveryPartner = DeliveryPartner.builder()
                .name(request.getName())
                .description(request.getDescription())
                .shippingFee(request.getShippingFee())
                .build();
        deliveryPartner.setEmail(request.getEmail());
        deliveryPartner.setPassword(passwordEncoder.encode(request.getPassword()));
        String avatarLink = request.getAvatar();
        if (avatarLink == null || avatarLink.isEmpty()) {
            avatarLink = Utils.generateAvatarLink(request.getName());
        }
        deliveryPartner.setAvatar(avatarLink);
        //TODO: enumerate role
        deliveryPartner.setRole("ROLE_DELIVERY_PARTNER");

        DeliveryPartner savedDeliveryPartner = deliveryPartnerRepository.save(deliveryPartner);


        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Delivery partner created successfully")
                .data(savedDeliveryPartner)
                .build());
    }

    @Override
    public ResponseEntity<Response> updateDeliveryPartner(UpdateDeliveryPartnerRequest deliveryPartner) {
        DeliveryPartner existingDeliveryPartner = findDeliveryPartnerById(deliveryPartner.getDeliveryPartnerId());

        if (deliveryPartner.getName() != null) existingDeliveryPartner.setName(deliveryPartner.getName());
        if (deliveryPartner.getDescription() != null) existingDeliveryPartner.setDescription(deliveryPartner.getDescription());
        if (deliveryPartner.getShippingFee() != null) existingDeliveryPartner.setShippingFee(deliveryPartner.getShippingFee());

        deliveryPartnerRepository.save(existingDeliveryPartner);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Delivery partner updated successfully")
                .data(null)
                .build());
    }

    @Override
    public ResponseEntity<Response> deleteDeliveryPartnerById(Long deliveryPartnerId) {
        DeliveryPartner existingDeliveryPartner = findDeliveryPartnerById(deliveryPartnerId); // check if delivery partner exists
        deliveryPartnerRepository.deleteById(deliveryPartnerId);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Delivery partner deleted successfully")
                .data(null)
                .build());
    }

    @Override
    public ResponseEntity<Response> getDeliveryPartnerById(Long deliveryPartnerId) {
        DeliveryPartner deliveryPartner = findDeliveryPartnerById(deliveryPartnerId);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Delivery partner retrieved successfully")
                .data(new DeliveryPartnerInformation(deliveryPartner))
                .build());
    }

    @Override
    public ResponseEntity<Response> getAllDeliveryPartners(Integer pageNumber, Integer elementsPerPage) {
        Pageable pageable = PageRequest.of(pageNumber, elementsPerPage);
        Page<DeliveryPartner> deliveryPartners = deliveryPartnerRepository.findAll(pageable);

        PageResponse pageResponse = PageResponse.builder()
                .content(DeliveryPartnerInformation.from(deliveryPartners.getContent()))
                .totalPages(deliveryPartners.getTotalPages())
                .size(deliveryPartners.getSize ())
                .build();

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Delivery partners retrieved successfully")
                .data(pageResponse)
                .build());
    }
}
