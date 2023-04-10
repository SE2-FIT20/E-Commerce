package com.example.ecommerce.service.impl;

import com.example.ecommerce.domain.Search;
import com.example.ecommerce.domain.User;
import com.example.ecommerce.dto.response.Response;
import com.example.ecommerce.repository.SearchRepository;
import com.example.ecommerce.service.service.SearchService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class SearchServiceImpl implements SearchService {
    private final SearchRepository searchRepository;

    @Override
    public ResponseEntity<Response> getLatestSearchesByUser(User user) {

        List<Search> latestSearches = searchRepository.findTop10ByUserOrderByCreatedAtDesc(user);
        Response response = Response.builder()
                .status(200)
                .message("Latest searches retrieved successfully")
                .data(latestSearches)
                .build();
        return ResponseEntity.ok(response);
    }

    @Override
    public Search saveSearch(Search search) {
        return searchRepository.save(search);
    }

    @Override
    public void deleteSearchById(Long searchId) {
        searchRepository.deleteById(searchId);
    }

    @Override
    public void saveSearchHistory(User currentUser, String keyword) {
        if (currentUser != null) {
            Search search = Search.builder()
                    .keyword(keyword)
                    .user(currentUser)
                    .createdAt(LocalDateTime.now())
                    .build();

            saveSearch(search);
        }
    }
}
