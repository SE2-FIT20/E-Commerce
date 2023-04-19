package com.example.ecommerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

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
@ToString(exclude = "vouchers")
public class VoucherSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "voucherSet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Voucher> vouchers;

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
        return vouchers.stream().filter(voucher -> !voucher.isUsed()).toArray().length;
    }

    public void addVoucher(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }


        for (int i = 0; i < quantity; i++) {
            String img = generateAvatarLink(String.valueOf(percent));
            String code = generateRandomString();
            Voucher voucher = new Voucher();
            voucher.setCode(code);
            voucher.setExpiredAt(expiredAt);
            voucher.setPercent(percent);
            voucher.setCreatedAt(LocalDateTime.now());
            voucher.setStartAt(startAt);
            voucher.setUsed(false);
            voucher.setImage(img);
            voucher.setDescription(description);
            voucher.setVoucherSet(this);
            vouchers.add(voucher);
        }
    }

    public void subtractVoucher(int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }

        if (vouchers.size() == 0) {
            throw new IllegalArgumentException("Coupon set is empty, cannot subtract more vouchers");
        }

        if (getQuantityAvailable() < quantity) {
            throw new IllegalArgumentException("Quantity must be less than or equal to the number of vouchers in the set");
        }

        Iterator<Voucher> iter = vouchers.iterator();
        while (iter.hasNext()) {
            Voucher voucher = iter.next();
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
