package com.example.ecommerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Iterator;
import java.util.List;

import static com.example.ecommerce.utils.Utils.generateAvatarLink;
import static com.example.ecommerce.utils.Utils.generateRandomString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CouponSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "couponSet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Coupon> coupons;

    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;
    private String name;
    private double percent;
    @Column(columnDefinition="LONGTEXT")
    private String description;
    private LocalDateTime startAt;
    private LocalDateTime createdAt;
    private LocalDateTime expiredAt;
    private String image;

    @Transient
    public int quantityAvailable;
    public int getQuantityAvailable() {
        return coupons.stream().filter(voucher -> !voucher.isUsed()).toArray().length;
    }

    public void addCoupon(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }


        for (int i = 0; i < quantity; i++) {
            String img = generateAvatarLink(String.valueOf(percent));
            String code = generateRandomString();
            Coupon voucher = new Coupon();
            voucher.setCode(code);
            voucher.setPercent(percent);
            voucher.setExpiredAt(expiredAt);
            voucher.setCreatedAt(LocalDateTime.now());
            voucher.setStartAt(startAt);
            voucher.setUsed(false);
            voucher.setImage(img);
            voucher.setDescription(description);
            voucher.setCouponSet(this);
            coupons.add(voucher);
        }
    }

    public void subtractCoupon(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        if (coupons.size() == 0) {
            throw new IllegalArgumentException("Coupon set is empty, cannot subtract more vouchers");
        }

        if (getQuantityAvailable() < quantity) {
            throw new IllegalArgumentException("Quantity must be less than or equal to the number of vouchers in the set");
        }

        Iterator<Coupon> iter = coupons.iterator();
        while (iter.hasNext()) {
            Coupon voucher = iter.next();
            if (!voucher.isUsed()) {
                iter.remove();
                quantity--;
            }
            if (quantity == 0) {
                break;
            }
        }
    }
}
