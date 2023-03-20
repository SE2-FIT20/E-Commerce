package com.example.ecommerce.service.impl;

import com.example.ecommerce.domain.Customer;
import com.example.ecommerce.domain.User;
import com.example.ecommerce.dto.request.customer.UpdateCustomerRequest;
import com.example.ecommerce.dto.response.CustomerInformation;
import com.example.ecommerce.dto.response.Response;
import com.example.ecommerce.exception.NotFoundException;
import com.example.ecommerce.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    public ResponseEntity<Response> getCustomerInformationById(Long customerId) {
        Customer customer = findCustomerById(customerId);
        CustomerInformation customerInformation = new CustomerInformation(customer);

        Response response = Response.builder()
                .status(200)
                .message("Get customer information successfully")
                .data(customerInformation)
                .build();

        return ResponseEntity.ok(response);
    }

    private Customer findCustomerById(Long customerId) {
        return customerRepository
                .findById(customerId)
                .orElseThrow(() -> new NotFoundException("Customer not found for id: " + customerId));
    }

    public ResponseEntity<Response> updateAccount(Long id, UpdateCustomerRequest request) {
        Customer customer = findCustomerById(id);

        if (request.getName() != null) customer.setName(request.getName());
        if (request.getAddress() != null) customer.setAddress(request.getAddress());
        if (request.getPhone() != null) customer.setPhoneNumber(request.getPhone());
        if (request.getAvatar() != null) customer.setAvatar(request.getAvatar());

        customerRepository.save(customer);


        Response response = Response.builder()
                .status(200)
                .message("Update customer information successfully")
                .data(null)
                .build();

        return ResponseEntity.ok(response);
    }
}
