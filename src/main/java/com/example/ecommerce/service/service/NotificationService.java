package com.example.ecommerce.service.service;

import com.example.ecommerce.domain.Notification;
import com.example.ecommerce.domain.User;
import com.example.ecommerce.dto.request.MarkNotificationsAsRead;
import com.example.ecommerce.dto.response.Response;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface NotificationService {
    void sendNotificationToUser(Long userId, Notification notification);

    ResponseEntity<Response> getNotificationById(User user, Long id);


    Notification findNotificationById(Long id);
    ResponseEntity<Response> deleteNotificationById(Long id);

    ResponseEntity<Response> readNotificationById(List<Long> ids);


    ResponseEntity<Response> getAllNotificationsByUser(User user, Integer page, Integer elementsPerPage, String status, String filter, String sortBy);

    ResponseEntity<Response> markNotificationsAsRead(User user, MarkNotificationsAsRead request);

}