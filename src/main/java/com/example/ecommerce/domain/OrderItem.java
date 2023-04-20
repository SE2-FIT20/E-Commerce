package com.example.ecommerce.domain;

import com.example.ecommerce.dto.response.ProductBriefInfo;
import com.example.ecommerce.dto.response.ProductDetailedInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;
    private Integer quantity;

    @Transient
    private Double price;

    @ManyToOne
    private Voucher voucher;
    @ManyToOne
    private Coupon coupon;

    private Double calculateOriginalPrice() {
        return product.getPrice() * quantity;
    }


    public void applyPromotion(Promotion promotion) {
        if (promotion instanceof Voucher) {
            this.voucher = (Voucher) promotion;
        } else if (promotion instanceof Coupon) {
            this.coupon = (Coupon) promotion;
        }
    }
    public Double getPrice() {
        if (coupon == null && voucher == null) {
            return calculateOriginalPrice();
        } else {
            return calculateDiscountPrice();
        }
    }

    private Double calculateDiscountPrice() {
        Double originalPrice = calculateOriginalPrice();
        if (coupon != null) {
            originalPrice = originalPrice * (1 - coupon.getPercent());
        }
        if (voucher != null) {
            originalPrice = originalPrice * (1 - voucher.getPercent());
        }
        return originalPrice;
    }

    public ProductBriefInfo getProduct() {
        return new ProductBriefInfo(product);
    }
}
