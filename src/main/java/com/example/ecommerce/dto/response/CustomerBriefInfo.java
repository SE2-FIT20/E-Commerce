package com.example.ecommerce.dto.response;

import com.example.ecommerce.domain.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerBriefInfo {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;

    public CustomerBriefInfo(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.email = customer.getEmail();
        this.phoneNumber = customer.getPhoneNumber();
    }
}
