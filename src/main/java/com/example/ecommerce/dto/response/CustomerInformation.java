package com.example.ecommerce.dto.response;

import com.example.ecommerce.domain.Customer;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerInformation {
    private Long id;
    private String name;
    private String email;
    private String address;
    private String phoneNumber;

    public CustomerInformation(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.email = customer.getEmail();
        this.address = customer.getAddress();
        this.phoneNumber = customer.getPhoneNumber();
    }
}
