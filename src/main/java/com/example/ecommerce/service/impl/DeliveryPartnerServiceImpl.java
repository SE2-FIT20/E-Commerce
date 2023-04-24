package com.example.ecommerce.service.impl;

import com.example.ecommerce.domain.DeliveryPartner;
import com.example.ecommerce.domain.Notification;
import com.example.ecommerce.domain.Order;
import com.example.ecommerce.dto.request.UpdateDeliveryPartnerAccountRequest;
import com.example.ecommerce.dto.request.deliveryPartner.CreateDeliveryPartnerRequest;
import com.example.ecommerce.dto.request.deliveryPartner.UpdateDeliveryPartnerRequest;
import com.example.ecommerce.dto.request.order.UpdateOrderRequest;
import com.example.ecommerce.dto.response.*;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.DeliveryPartnerRepository;
import com.example.ecommerce.service.service.DeliveryPartnerService;
import com.example.ecommerce.service.service.NotificationService;
import com.example.ecommerce.service.service.OrderService;
import com.example.ecommerce.service.service.UserService;
import com.example.ecommerce.utils.Utils;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@AllArgsConstructor
public class DeliveryPartnerServiceImpl implements DeliveryPartnerService {

    private final DeliveryPartnerRepository deliveryPartnerRepository;
    private final UserService userService;
    private final OrderService orderService;
    private final PasswordEncoder passwordEncoder;
    private final NotificationService notificationService;
    @Override
    public DeliveryPartner findDeliveryPartnerById(Long deliveryPartnerId) {
        return deliveryPartnerRepository.findById(deliveryPartnerId)
                .orElseThrow(() -> new NotFoundException("Delivery partner not found with id: " + deliveryPartnerId));
    }

    @Override
    public ResponseEntity<Response> createDeliveryPartner(CreateDeliveryPartnerRequest request) {

        DeliveryPartner deliveryPartner = DeliveryPartner.builder()
                .description(request.getDescription())
                .shippingFee(request.getShippingFee())
                .build();

        deliveryPartner.setName(request.getName());
        deliveryPartner.setEmail(request.getEmail());
        deliveryPartner.setPassword(passwordEncoder.encode(request.getPassword()));
        String avatarLink = request.getAvatar();
        if (avatarLink == null || avatarLink.isEmpty()) {
            avatarLink = Utils.generateAvatarLink(request.getName());
        }
        deliveryPartner.setAvatar(avatarLink);
        //TODO: enumerate role
        deliveryPartner.setRole("DELIVERY_PARTNER");
        deliveryPartner.setCreatedAt(LocalDateTime.now());

         deliveryPartnerRepository.save(deliveryPartner);


        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Delivery partner created successfully")
                .data(null)
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
                .data(new DeliveryPartnerBriefInformation(deliveryPartner))
                .build());
    }

    @Override
    public ResponseEntity<Response> getAllDeliveryPartners(Integer pageNumber, Integer elementsPerPage) {
        Pageable pageable = PageRequest.of(pageNumber, elementsPerPage);
        Page<DeliveryPartner> deliveryPartners = deliveryPartnerRepository.findAll(pageable);

        PageResponse pageResponse = PageResponse.builder()
                .content(DeliveryPartnerBriefInformation.from(deliveryPartners.getContent()))
                .totalPages(deliveryPartners.getTotalPages())
                .size(deliveryPartners.getSize ())
                .build();

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Delivery partners retrieved successfully")
                .data(pageResponse)
                .build());
    }

    @Override
    public ResponseEntity<Response> getAllOrderByDeliveryPartners(Integer pageNumber, Integer elementsPerPage, Long deliveryPartnerId, String status, String filter, String sortBy, String from, String to) {
        DeliveryPartner deliveryPartner = findDeliveryPartnerById(deliveryPartnerId);

        Pageable pageable = PageRequest.of(pageNumber, elementsPerPage, Sort.Direction.valueOf(sortBy.toUpperCase()), filter);

         Page<Order> page;

        if (status != null && !status.equalsIgnoreCase("ALL")) {
            Order.OrderStatus orderStatus = Order.OrderStatus.valueOf(status.toUpperCase());
            page = orderService.getAllByDeliveryPartnerAndStatus(deliveryPartner, orderStatus, pageable);
        } else {
            page = orderService.getAllByDeliveryPartner(deliveryPartner, pageable);
        }

        PageResponse pageResponse = PageResponse.builder()
                .content(page.getContent())
                .totalPages(page.getTotalPages())
                .size(page.getSize())
                .build();

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Orders retrieved successfully")
                .data(pageResponse)
                .build());

    }

    @Override
    public ResponseEntity<Response> getOrderById(Long deliveryPartnerId, Long orderId) {
        Order order = orderService.findOrderById(orderId);

        // check if order belongs to delivery partner
        if (!order.getDeliveryPartner().getId().equals(deliveryPartnerId)) {
            throw new IllegalStateException("Order does not belong to delivery partner");
        }

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Order retrieved successfully")
                .data(order)
                .build());

    }

    @Override
    public ResponseEntity<Response> updateOrder(Long deliveryPartnerId, UpdateOrderRequest updateRequest) {

        Order order = orderService.findOrderById(updateRequest.getOrderId());
        // check if order belongs to delivery partner
        if (!order.getDeliveryPartner().getId().equals(deliveryPartnerId)) {
            throw new IllegalStateException("Order does not belong to delivery partner");
        }


        orderService.updateOrder(updateRequest);

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Order updated successfully")
                .data(null)
                .build());
    }

    private void sendNotificationForCustomer(Order order, CustomerBriefInfo customer) {
        Notification notification = Notification.builder()
                .type(Notification.NotificationType.ORDER_STATUS_CHANGED)
                .content("Your order has been delivered")
                .order(order)
                .build();

        notificationService.sendNotificationToUser(customer.getId(), notification);
    }

    @Override
    public ResponseEntity<Response> getAccountInformation(Long id) {
        DeliveryPartner deliveryPartner = findDeliveryPartnerById(id);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Account information retrieved successfully")
                .data(new DeliveryPartnerDetailedInformation(deliveryPartner))
                .build());
    }

    @Override
    public ResponseEntity<Response> updateAccount(Long id, UpdateDeliveryPartnerAccountRequest updateAccountRequest) {

        DeliveryPartner deliveryPartner = findDeliveryPartnerById(id);

        if (updateAccountRequest.getDescription() != null) deliveryPartner.setDescription(updateAccountRequest.getDescription());
        if (updateAccountRequest.getShippingFee() != null) deliveryPartner.setShippingFee(updateAccountRequest.getShippingFee());
        if (updateAccountRequest.getAvatar() != null) deliveryPartner.setAvatar(updateAccountRequest.getAvatar());

        deliveryPartnerRepository.save(deliveryPartner);

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Account updated successfully")
                .data(null)
                .build());
    }

    @Override
    public ResponseEntity<Response> countOrders(Long id, LocalDateTime fromDateTime, LocalDateTime toDateTime) {

        DeliveryPartner deliveryPartner = findDeliveryPartnerById(id);

        Map<String, Long> count = orderService.countOrdersByDeliveryPartner(deliveryPartner, fromDateTime, toDateTime);

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Orders count retrieved successfully")
                .data(count)
                .build());
    }
}
