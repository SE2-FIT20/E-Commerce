package com.example.ecommerce.repository;

import com.example.ecommerce.domain.MiniGamePlayingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MiniGamePlayingRecordRepository extends JpaRepository<MiniGamePlayingRecord, Long> {

    List<MiniGamePlayingRecord> findAllByCustomer_IdAndCreatedAtBetween(Long customerId, LocalDateTime start, LocalDateTime end);
}
