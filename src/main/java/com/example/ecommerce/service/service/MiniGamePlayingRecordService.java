package com.example.ecommerce.service.service;

import com.example.ecommerce.domain.Customer;

public interface MiniGamePlayingRecordService {
    boolean checkIfCustomerSavedAVoucherToday(Long  customerId);

    void saveMiniGamePlayingRecord(Customer id);

}
