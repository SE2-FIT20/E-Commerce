package com.example.ecommerce.service.impl;

import com.example.ecommerce.domain.Search;
import com.example.ecommerce.domain.User;
import com.example.ecommerce.dto.response.Response;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.SearchRepository;
import com.example.ecommerce.service.service.SearchService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
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
    public ResponseEntity<Response> deleteSearchById(User user, Long searchId) {
        Search search = findSearchById(searchId); // check if the search exists
        if (!search.getUser().getId().equals(user.getId())) {
            throw new IllegalStateException("You are not authorized to delete this search");
        }
        searchRepository.deleteById(searchId);
        Response response = Response.builder()
                .status(200)
                .message("Search deleted successfully")
                .build();
        return ResponseEntity.ok(response);
    }

    private Search findSearchById(Long searchId) {
        return searchRepository.findById(searchId).orElseThrow(() -> new NotFoundException("Search not found for id: " + searchId));
    }


    @Transactional
    @Override
    public void saveSearchHistory(User currentUser, String keyword) {
        if (currentUser != null) {
            // delete the old search history, to avoid duplication
            searchRepository.deleteByKeyword(keyword);
            Search search = Search.builder()
                    .keyword(keyword)
                    .user(currentUser)
                    .createdAt(LocalDateTime.now())
                    .build();

            saveSearch(search);
        }
    }
}
