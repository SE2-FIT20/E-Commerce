package com.example.ecommerce.service.impl;

import com.example.ecommerce.domain.User;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void save(User user) {
        userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email).orElseThrow(() -> new NotFoundException("User not found"));
    }
}
