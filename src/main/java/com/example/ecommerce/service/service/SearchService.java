package com.example.ecommerce.service.service;

import com.example.ecommerce.domain.Search;
import com.example.ecommerce.domain.User;
import com.example.ecommerce.dto.response.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface SearchService {
    ResponseEntity<Response> getLatestSearchesByUser(User user);
    Search saveSearch(Search search);
    void deleteSearchById(Long searchId);

    void saveSearchHistory(User currentUser, String keyword);
}
