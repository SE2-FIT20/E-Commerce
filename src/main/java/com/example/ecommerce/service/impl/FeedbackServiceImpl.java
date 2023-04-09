package com.example.ecommerce.service.impl;

import com.example.ecommerce.domain.Feedback;
import com.example.ecommerce.dto.response.PageResponse;
import com.example.ecommerce.dto.response.Response;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.FeedbackRepository;
import com.example.ecommerce.service.service.FeedbackService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class FeedbackServiceImpl implements FeedbackService {

    private final FeedbackRepository feedbackRepository;

    @Autowired
    public FeedbackServiceImpl(FeedbackRepository feedbackRepository) {
        this.feedbackRepository = feedbackRepository;
    }

    @Override
    public ResponseEntity<Response> resolveFeedback(Long feedbackId) {
        Feedback feedback =  findFeedbackById(feedbackId);
        feedback.setResolved(true);

        if (feedback.isResolved()) {
           throw new IllegalStateException("Feedback is already resolved");
        }
        feedbackRepository.save(feedback);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Resolve feedback successfully!")
                .build());
    }

    @Override
    public ResponseEntity<Response> getFeedbacks(Integer pageNumber, Integer elementsPerPage, String status) {
        //TODO: enumerate status
        Pageable pageable = PageRequest.of(pageNumber, elementsPerPage);
        Page<Feedback> feedbacks;
        if (status.toUpperCase().equals("RESOLVED")) {
            feedbacks =  findByResolved(true, pageNumber, elementsPerPage);
        } else if (status.toUpperCase().equals("UNRESOLVED")) {
            feedbacks = findByResolved(false, pageNumber, elementsPerPage);
        } else {
            feedbacks = feedbackRepository.findAll(pageable);
        }


        PageResponse pageResponse = PageResponse.builder()
                .content(feedbacks.getContent())
                .totalPages(feedbacks.getTotalPages())
                .size(feedbacks.getSize())
                .build();

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get feedbacks successfully!")
                .data(pageResponse)
                .build());
    }

    @Override
    public Page<Feedback> findByResolved(boolean isResolved, Integer pageNumber, Integer elementsPerPage) {
        Pageable pageable = PageRequest.of(pageNumber, elementsPerPage);
        Page<Feedback> feedbacks = feedbackRepository.findByIsResolved(isResolved, pageable);
        return feedbacks;
    }

    @Override
    public ResponseEntity<Response> findById(Long id) {
        Feedback feedback =  findFeedbackById(id);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get feedback successfully!")
                .data(feedback)
                .build());
    }

    @Override
    public Feedback findFeedbackById(Long id) {
        Feedback feedback =  feedbackRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Could not find feedback with id " + id));
        return feedback;
    }

    @Override
    public ResponseEntity<Response> save(Feedback feedback) {
         feedbackRepository.save(feedback);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Save feedback successfully!")
                .data(feedback)
                .build());
    }

    @Override
    public ResponseEntity<Response> deleteById(Long id) {
        Feedback feedback =  findFeedbackById(id); // check if feedback exists
        feedbackRepository.deleteById(id);

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Delete feedback successfully!")
                .build());
    }

}