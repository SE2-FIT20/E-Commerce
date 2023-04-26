package com.example.ecommerce.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@DiscriminatorValue("voucher")
@Data
public class Voucher extends Promotion {

    // no additional attributes or relationships

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private VoucherSet voucherSet;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private MiniGamePlayingRecord miniGamePlayingRecord;
    @Override
    public double getPercent() {
        return voucherSet.getPercent();
    }

    @Override
    public String getStatus() {
        return voucherSet.getStatus();
    }

    @Override
    public LocalDateTime getStartAt() {
        return voucherSet.getStartAt();
    }

    @Override
    public LocalDateTime getExpiredAt() {
        return voucherSet.getExpiredAt();
    }

    @Override
    public String getImage() {
        return voucherSet.getImage();
    }

    @Override
    public String getDescription() {
        return voucherSet.getDescription();
    }
}