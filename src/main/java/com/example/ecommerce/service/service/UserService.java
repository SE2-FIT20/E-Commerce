package com.example.ecommerce.service.service;

import com.example.ecommerce.domain.User;
import com.example.ecommerce.dto.request.auth.ChangeAccessRequest;
import com.example.ecommerce.dto.response.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    ResponseEntity<Response> createUser(User user);
    ResponseEntity<Response> deleteUserById(Long userId);
    ResponseEntity<Response> updateUser(Long userId, User user);
    ResponseEntity<Response> getUserById(Long userId);

    ResponseEntity<Response> getAllUsers(Integer page, Integer elementsPerPage, String filter, String sortBy, String status, String role);
    User findByEmail(String email);
    ResponseEntity<Response> changeAccess(ChangeAccessRequest request);

    ResponseEntity<Response> getUserInformationById(Long id);

}
