package com.example.ecommerce.service.service;

import com.example.ecommerce.domain.Feedback;
import com.example.ecommerce.dto.response.Response;
import com.example.ecommerce.exception.NotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface FeedbackService {

    ResponseEntity<Response> resolveFeedback(Long feedbackId);


    Feedback findFeedbackById(Long id);

    Page<Feedback> findByResolved(boolean isResolved, Integer pageNumber, Integer elementsPerPage);

    ResponseEntity<Response> findById(Long id);

    ResponseEntity<Response> save(Feedback feedback);

    ResponseEntity<Response> deleteById(Long id);

    ResponseEntity<Response> getFeedbacks(Integer page, Integer elementsPerPage, String status);
}