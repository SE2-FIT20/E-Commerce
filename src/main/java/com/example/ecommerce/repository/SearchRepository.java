package com.example.ecommerce.repository;

import com.example.ecommerce.domain.Search;
import com.example.ecommerce.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SearchRepository extends JpaRepository<Search, Long> {
    List<Search> findTop10ByUserOrderByCreatedAtDesc(User user);

    void deleteByKeyword(String keyword);
}