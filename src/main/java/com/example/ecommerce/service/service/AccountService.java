package com.example.ecommerce.service.service;

import com.example.ecommerce.domain.Customer;
import com.example.ecommerce.dto.response.Response;
import org.springframework.http.ResponseEntity;

public interface AccountService {

    ResponseEntity<Response> login(String username, String password);
    ResponseEntity<Response> register(String username, String password, String email);
    ResponseEntity<Response> logout(String username);
    ResponseEntity<Response> changePassword(String username, String oldPassword, String newPassword);
    ResponseEntity<Response> updateAccount(Customer customer);

}
