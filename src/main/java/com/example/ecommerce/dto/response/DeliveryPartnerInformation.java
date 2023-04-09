package com.example.ecommerce.dto.response;

import com.example.ecommerce.domain.DeliveryPartner;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class DeliveryPartnerInformation {
    private Long id;
    private String avatar;
    private String email;
    private String name;
    private String description;
    private Double shippingFee;

    public DeliveryPartnerInformation(DeliveryPartner deliveryPartner) {
        this.id = deliveryPartner.getId();
        this.avatar = deliveryPartner.getAvatar();
        this.email = deliveryPartner.getEmail();
        this.name = deliveryPartner.getName();
        this.description = deliveryPartner.getDescription();
        this.shippingFee = deliveryPartner.getShippingFee();
    }

    public static List<DeliveryPartnerInformation> from(List<DeliveryPartner> deliveryPartners) {
        return deliveryPartners.stream().map(DeliveryPartnerInformation::new).toList();
    }
}