package com.bank.ai.customerservice.repository;

import com.bank.ai.customerservice.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Optional<Customer> findByEmail(String email);

    boolean existsByEmail(String email);

        boolean existsByEmailAndCustomerIdNot(
            String email,
            Long customerId
    );
}
