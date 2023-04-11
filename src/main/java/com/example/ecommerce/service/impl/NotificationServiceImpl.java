package com.example.ecommerce.service.impl;

import com.example.ecommerce.domain.Notification;
import com.example.ecommerce.domain.User;
import com.example.ecommerce.dto.request.MarkNotificationsAsRead;
import com.example.ecommerce.dto.response.PageResponse;
import com.example.ecommerce.dto.response.Response;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.NotificationRepository;
import com.example.ecommerce.service.service.NotificationService;
import com.example.ecommerce.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private UserService userService;

    @Override
    public void sendNotificationToUser(Long userId, Notification notification) {
        User user = userService.findUserById(userId);
        notification.setUser(user);
        notificationRepository.save(notification);
    }

    @Override
    public ResponseEntity<Response> getNotificationById(User user, Long id) {
        Notification notification = findNotificationById(id);
        if (!notification.getUser().getId().equals(user.getId())) {
            throw new NotFoundException("You are not allowed to access this notification");
        }
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get notification successfully")
                .data(notification)
                .build());
    }

    @Override
    public Notification findNotificationById(Long id) {
        return notificationRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Notification not found for id: " + id));
    }

    @Override
    public ResponseEntity<Response> deleteNotificationById(Long id) {
        Notification notification = findNotificationById(id); // check if notification exists
        notificationRepository.delete(notification);

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Delete notification successfully")
                .build());
    }

    @Override
    public ResponseEntity<Response> readNotificationById(List<Long> ids) {
        List<Notification> notifications = notificationRepository.findAllById(ids);
        notifications.forEach(notification -> notification.setRead(true));
        notificationRepository.saveAll(notifications);

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Read notification successfully")
                .build());
    }

    @Override
    public ResponseEntity<Response> getAllNotificationsByUser(User user, Integer pageNumber, Integer elementsPerPage, String status, String filter, String sortBy) {
        Pageable pageable = PageRequest.of(pageNumber, elementsPerPage, Sort.by(Sort.Direction.valueOf(sortBy.toUpperCase()), filter));

        Notification notification = Notification.builder()
                .user(user)
                .build();

        if (!status.equalsIgnoreCase("all")) {
            if (status.equalsIgnoreCase("read")) {
                notification.setRead(true);
            } else {
                notification.setRead(false);
            }
        }

        ExampleMatcher matcher = ExampleMatcher.matching()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Notification> example = Example.of(notification, matcher);

        Page<Notification> notifications = notificationRepository.findAll(example, pageable);
        PageResponse pageResponse = PageResponse.builder()
                .content(notifications.getContent())
                .totalPages(notifications.getTotalPages())
                .size(notifications.getSize())
                .build();


        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get all notifications successfully")
                .data(pageResponse)
                .build());
    }

    @Override
    public ResponseEntity<Response> markNotificationsAsRead(User user, MarkNotificationsAsRead request) {


        List<Notification> userNotifications = user.getNotifications();
        for (Long id : request.getIds()) {
            if (!userNotifications.stream().anyMatch(notification -> notification.getId().equals(id))) {
                throw new NotFoundException("You are not allowed to access the notification with id: " + id);
            }
        }


        List<Notification> notifications = notificationRepository.findAllById(request.getIds());
        notifications.forEach(notification -> notification.setRead(true));
        notificationRepository.saveAll(notifications);

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Mark notifications as read successfully")
                .build());
    }


}