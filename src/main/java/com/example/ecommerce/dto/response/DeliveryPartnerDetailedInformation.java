package com.example.ecommerce.dto.response;

import com.example.ecommerce.domain.DeliveryPartner;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
// for the data that can only be shown to the delivery partner itself
public class DeliveryPartnerDetailedInformation {
    private Long id;
    private String avatar;
    private String email;
    private String name;
    private String description;
    private Double shippingFee;
    private Double balance;
    public DeliveryPartnerDetailedInformation(DeliveryPartner deliveryPartner) {
        this.id = deliveryPartner.getId();
        this.avatar = deliveryPartner.getAvatar();
        this.email = deliveryPartner.getEmail();
        this.name = deliveryPartner.getName();
        this.description = deliveryPartner.getDescription();
        this.shippingFee = deliveryPartner.getShippingFee();
        this.balance = deliveryPartner.getBalance();
    }

    public static List<DeliveryPartnerDetailedInformation> from(List<DeliveryPartner> deliveryPartners) {
        return deliveryPartners.stream().map(DeliveryPartnerDetailedInformation::new).toList();
    }
}