package com.bank.ai.customerservice.service;

import com.bank.ai.customerservice.dto.CustomerRequest;
import com.bank.ai.customerservice.dto.CustomerResponse;
import com.bank.ai.customerservice.dto.PageResponse;
import org.springframework.data.domain.Pageable;

// import java.util.List;

public interface CustomerService {

    CustomerResponse createCustomer(CustomerRequest request);

    CustomerResponse getCustomerById(Long customerId);

    // List<CustomerResponse> getAllCustomers();
    PageResponse<CustomerResponse> getAllCustomers(Pageable pageable);

    CustomerResponse updateCustomer(
            Long customerId,
            CustomerRequest request
    );

    void deleteCustomer(Long customerId);
}
