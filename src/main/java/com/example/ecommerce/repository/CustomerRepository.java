package com.example.ecommerce.repository;

import com.example.ecommerce.domain.Customer;
import com.example.ecommerce.dto.response.CustomerInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    @Query("SELECT new com.example.ecommerce.dto.response.CustomerInformation(c.id, c.name, c.email, c.addresses, c.phoneNumber, c.avatar) FROM Customer c WHERE c.id = :id")
    CustomerInformation findCustomerNameEmailAddressesAndPhoneNumberById(@Param("id") Long id);
}
