package com.example.ecommerce.service.impl;

import com.example.ecommerce.domain.Customer;
import com.example.ecommerce.domain.MiniGamePlayingRecord;
import com.example.ecommerce.repository.MiniGamePlayingRecordRepository;
import com.example.ecommerce.service.service.MiniGamePlayingRecordService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class MiniGamePlayingRecordServiceImpl implements MiniGamePlayingRecordService {

    private final MiniGamePlayingRecordRepository miniGamePlayingRecordRepository;

    @Override
    public boolean checkIfCustomerSavedAVoucherToday(Long customerId) {
        LocalDateTime start = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime end = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        return miniGamePlayingRecordRepository.findAllByCustomer_IdAndCreatedAtBetween(customerId, start, end).size() > 0;
    }

    @Override
    public void saveMiniGamePlayingRecord(Customer customer) {
        MiniGamePlayingRecord miniGamePlayingRecord = MiniGamePlayingRecord.builder()
                .customer(customer)
                .createdAt(LocalDateTime.now())
                .build();

        miniGamePlayingRecordRepository.save(miniGamePlayingRecord);
    }
}
