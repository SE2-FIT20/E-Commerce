package com.example.ecommerce.dto.response;

import com.example.ecommerce.domain.DeliveryPartner;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
// for the data that can be publicly shown
public class DeliveryPartnerBriefInformation {
    private Long id;
    private String avatar;
    private String email;
    private String name;
    private String description;
    private Double shippingFee;

    public DeliveryPartnerBriefInformation(DeliveryPartner deliveryPartner) {
        this.id = deliveryPartner.getId();
        this.avatar = deliveryPartner.getAvatar();
        this.email = deliveryPartner.getEmail();
        this.name = deliveryPartner.getName();
        this.description = deliveryPartner.getDescription();
        this.shippingFee = deliveryPartner.getShippingFee();
    }

    public static List<DeliveryPartnerBriefInformation> from(List<DeliveryPartner> deliveryPartners) {
        return deliveryPartners.stream().map(DeliveryPartnerBriefInformation::new).toList();
    }
}