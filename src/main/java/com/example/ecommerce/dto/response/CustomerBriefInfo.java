package com.example.ecommerce.dto.response;

import com.example.ecommerce.domain.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
// for the data that can be publicly shown
public class CustomerBriefInfo {
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String avatar;
    public CustomerBriefInfo(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.email = customer.getEmail();
        this.phoneNumber = customer.getPhoneNumber();
        this.avatar = customer.getAvatar();
    }
}
