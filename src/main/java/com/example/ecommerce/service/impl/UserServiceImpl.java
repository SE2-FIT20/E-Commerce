package com.example.ecommerce.service.impl;

import com.example.ecommerce.domain.User;
import com.example.ecommerce.dto.request.UpdateAdminRequest;
import com.example.ecommerce.dto.request.auth.ChangeAccessRequest;
import com.example.ecommerce.dto.response.PageResponse;
import com.example.ecommerce.dto.response.Response;
import com.example.ecommerce.dto.response.UserInformation;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.UserRepository;
import com.example.ecommerce.service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<Response> save(User user) {
        userRepository.save(user);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Save user successfully")
                .build());
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
                .data(new UserInformation(user))
                .build());
    }

    public User findUserById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public ResponseEntity<Response> searchUserByName(String name, Integer page, Integer elementsPerPage, String status, String filter, String sortBy, String role) {
        Pageable pageable = PageRequest.of(page, elementsPerPage, Sort.Direction.valueOf(sortBy.toUpperCase()), filter);
        User user = new User();
        user.setName(name);

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        if (!role.equalsIgnoreCase("ALL")) {
            user.setRole(role);
        }


        if (!status.equalsIgnoreCase("ALL")) {
            if (status.equalsIgnoreCase("LOCKED")) {
                user.setLocked(true); // only get locked users
            } else {
                user.setLocked(false); // only get unlocked users
            }
        } else {
            matcher = matcher.withIgnorePaths("isLocked"); // ignore locked field, since it is false by default
        }

        Example<User> example = Example.of(user, matcher);
        Page<User> users = userRepository.findAll(example, pageable);
        PageResponse pageResponse = PageResponse.builder()
                .totalPages(users.getTotalPages())
                .content(UserInformation.from(users.getContent()))
                .build();
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get users successfully")
                .data(pageResponse)
                .build());
    }

    @Override
    public ResponseEntity<Response> searchUserByEmail(String email) {
        //TODO: search john@gmail.com -> johndue@gmail.com
        List<User> userList = userRepository.findAllByEmailContaining(email.toLowerCase());
        List<UserInformation> userInformationList = UserInformation.from(userList);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get user successfully")
                .data(userInformationList)
                .build());
    }

    @Override
    public ResponseEntity<Response> updateAccountInfoById(Long id, UpdateAdminRequest updateAccountRequest) {
        User user = findUserById(id);

        if (updateAccountRequest.getName() != null) user.setName(updateAccountRequest.getName());
        if (updateAccountRequest.getAvatar() != null) user.setAvatar(updateAccountRequest.getAvatar());

        userRepository.save(user);

        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Update user successfully")
                .build());
    }

    @Override
    public ResponseEntity<Response> getAllUsers(Integer page, Integer elementsPerPage, String filter, String sortBy, String status, String role) {

        Pageable pageable = PageRequest.of(page, elementsPerPage, Sort.Direction.valueOf(sortBy.toUpperCase()), filter);
        User user = new User();
        if (!role.equalsIgnoreCase("ALL")) {
            user.setRole(role);
        }

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        if (!status.equalsIgnoreCase("ALL")) {
            if (status.equalsIgnoreCase("LOCKED")) {
                user.setLocked(true); // only get locked users
            } else {
                user.setLocked(false); // only get unlocked users
            }
        } else {
            matcher = matcher.withIgnorePaths("isLocked"); // ignore locked field, since it is false by default
        }

        Example<User> example = Example.of(user, matcher);

        Page<User> users = userRepository.findAll(example, pageable);

        PageResponse pageResponse = PageResponse.builder()
                .totalPages(users.getTotalPages())
                .content(UserInformation.from(users.getContent()))
                .size(users.getSize())
                .build();


        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get all users successfully")
                .data(pageResponse)
                .build());
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("User not found"));
    }

    @Override
    public ResponseEntity<Response> changeAccess(ChangeAccessRequest request) {
        User user = findUserById(request.getUserId());
        if (request.getOperation().equals("LOCK")) {
            if (user.isLocked()) throw new IllegalStateException("User is already locked");
            if (user.getRole().equals("ADMIN")) throw new IllegalStateException("Admin can not be locked");
            user.setLocked(true);
        } else {
            if (!user.isLocked()) throw new IllegalStateException("User is already unlocked");
            user.setLocked(false);
        }
        userRepository.save(user);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Change access successfully!")
                .build());
    }

    @Override
    public ResponseEntity<Response> getUserInformationById(Long id) {
        User user = findUserById(id);
        return ResponseEntity.ok(Response.builder()
                .status(200)
                .message("Get user information successfully")
                .data(new UserInformation(user))
                .build());
    }


}
