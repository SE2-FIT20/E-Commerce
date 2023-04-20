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
@ToString(exclude = "vouchers")
@DiscriminatorValue("voucher_set")
public class VoucherSet extends PromotionSet{

    @OneToMany(mappedBy = "voucherSet", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Voucher> vouchers;

    @Override
    public int getQuantityAvailable() {
        return vouchers.stream().filter(voucher -> !voucher.isUsed()).toArray().length;
    }

    @Override
    public void addItems(int quantity) {
        for (int i = 0; i < quantity; i++) {
            Voucher voucher = new Voucher();
            voucher.setVoucherSet(this);
            voucher.setCreatedAt(LocalDateTime.now());
        }
    }

    @Override
    public void subtractItems(int quantity) {
        Iterator<Voucher> iterator = vouchers.iterator();
        while (iterator.hasNext()) {
            Voucher voucher = iterator.next();
            // only remove unused voucher
            if (!voucher.isUsed()) {
                iterator.remove();
                quantity--;
            }
            if (quantity == 0) {
                break;
            }
        }
    }

    @Override
    public Promotion getAnUnUsedItem() {
        return vouchers.stream()
                .filter(voucher -> !voucher.isUsed())
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No voucher available for this voucher set"));
    }
}
