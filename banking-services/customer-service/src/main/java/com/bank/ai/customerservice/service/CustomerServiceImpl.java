package com.bank.ai.customerservice.service;

import com.bank.ai.customerservice.dto.CustomerRequest;
import com.bank.ai.customerservice.dto.CustomerResponse;
import com.bank.ai.customerservice.dto.PageResponse;
import com.bank.ai.customerservice.entity.Customer;
import com.bank.ai.customerservice.exception.CustomerNotFoundException;
import com.bank.ai.customerservice.exception.DuplicateCustomerException;
import com.bank.ai.customerservice.repository.CustomerRepository;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Pageable;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    @Override
    public CustomerResponse createCustomer(CustomerRequest request) {

        if (customerRepository.existsByEmail(request.email())) {
            throw new DuplicateCustomerException(
        "Customer already exists with email: " + request.email()
);
        }

        Customer customer = Customer.builder()
                .firstName(request.firstName())
                .lastName(request.lastName())
                .email(request.email())
                .phoneNumber(request.phoneNumber())
                .dateOfBirth(request.dateOfBirth())
                .build();

        Customer savedCustomer = customerRepository.save(customer);

        return mapToResponse(savedCustomer);
    }

@Cacheable(
        value = "customers",
        key = "#customerId"
)
    @Override
    @Transactional(readOnly = true)
    public CustomerResponse getCustomerById(Long customerId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new CustomerNotFoundException(
                                "Customer not found with id: " + customerId
                        )
                );

        return mapToResponse(customer);
    }

    // @Override
    // @Transactional(readOnly = true)
    // public List<CustomerResponse> getAllCustomers() {

    //     return customerRepository.findAll()
    //             .stream()
    //             .map(this::mapToResponse)
    //             .toList();
    // }

    @Override
@Transactional(readOnly = true)
public PageResponse<CustomerResponse> getAllCustomers(Pageable pageable) {

    var customerPage = customerRepository.findAll(pageable);

    var content = customerPage.getContent()
            .stream()
            .map(this::mapToResponse)
            .toList();

    return new PageResponse<>(
            content,
            customerPage.getNumber(),
            customerPage.getSize(),
            customerPage.getTotalElements(),
            customerPage.getTotalPages(),
            customerPage.isFirst(),
            customerPage.isLast()
    );
}
@CacheEvict(
        value = "customers",
        key = "#customerId"
)
    @Override
    public CustomerResponse updateCustomer(
            Long customerId,
            CustomerRequest request
    ) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new CustomerNotFoundException(
                                "Customer not found with id: " + customerId
                        )
                );

                if (customerRepository.existsByEmailAndCustomerIdNot(
        request.email(),
        customerId)) {

    throw new DuplicateCustomerException(
            "Customer already exists with email: "
                    + request.email()
    );
}
        customer.setFirstName(request.firstName());
        customer.setLastName(request.lastName());
        customer.setEmail(request.email());
        customer.setPhoneNumber(request.phoneNumber());
        customer.setDateOfBirth(request.dateOfBirth());

        return mapToResponse(customer);
    }

@CacheEvict(
        value = "customers",
        key = "#customerId"
)
    @Override
    public void deleteCustomer(Long customerId) {

        if (!customerRepository.existsById(customerId)) {
            throw new CustomerNotFoundException(
                    "Customer not found with id: " + customerId
            );
        }

        customerRepository.deleteById(customerId);
    }

    private CustomerResponse mapToResponse(Customer customer) {

        return new CustomerResponse(
                customer.getCustomerId(),
                customer.getFirstName(),
                customer.getLastName(),
                customer.getEmail(),
                customer.getPhoneNumber(),
                customer.getDateOfBirth(),
                customer.getCreatedAt(),
                customer.getUpdatedAt()
        );
    }
}
