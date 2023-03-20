package com.example.ecommerce.service.impl;

import com.example.ecommerce.domain.User;
import com.example.ecommerce.dto.request.auth.ChangeAccessRequest;
import com.example.ecommerce.dto.response.Response;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<Response> createUser(User user) {
        return null;
    }

    @Override
    public ResponseEntity<Response> deleteUserById(Long userId) {
        userRepository.deleteById(userId);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Delete user successfully")
                .build());
    }

    @Override
    public ResponseEntity<Response> updateUser(Long userId, User user) {
        return null;
    }

    @Override
    public ResponseEntity<Response> getUserById(Long userId) {
        User user = findUserById(userId);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get user successfully")
                .data(user)
                .build());
    }

    private User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public ResponseEntity<Response> getAllUsers() {
        List<User> users = userRepository.findAll();
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get all users successfully")
                .data(userRepository.findAll())
                .build());
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public ResponseEntity<Response> changeAccess(ChangeAccessRequest request) {
        Optional<User> user = userRepository.findById(request.getUserId());
        return ResponseEntity.ok(Response.builder()
                .status(200).message("Change access successfully!")
                .data(user)
                .build());
    }
}
