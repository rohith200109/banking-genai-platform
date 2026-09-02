package com.bank.ai.customerservice.controller;

import com.bank.ai.customerservice.dto.CustomerRequest;
import com.bank.ai.customerservice.dto.CustomerResponse;
import com.bank.ai.customerservice.dto.PageResponse;
import com.bank.ai.customerservice.service.CustomerService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
// import org.springframework.data.web.PageableDefault;
// import com.bank.ai.customerservice.dto.PageResponse;
// import java.util.List;

@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(
            @Valid @RequestBody CustomerRequest request) {

        CustomerResponse response =
                customerService.createCustomer(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> getCustomer(
            @PathVariable Long customerId) {

        return ResponseEntity.ok(
                customerService.getCustomerById(customerId)
        );
    }

    // @GetMapping
    // public ResponseEntity<List<CustomerResponse>> getAllCustomers() {

    //     return ResponseEntity.ok(
    //             customerService.getAllCustomers()
    //     );
    // }
@GetMapping
public ResponseEntity<PageResponse<CustomerResponse>> getAllCustomers(
        @PageableDefault(
                size = 10,
                sort = "customerId",
                direction = Sort.Direction.DESC
        )
        Pageable pageable) {

    return ResponseEntity.ok(
            customerService.getAllCustomers(pageable)
    );
}

    @PutMapping("/{customerId}")
    public ResponseEntity<CustomerResponse> updateCustomer(
            @PathVariable Long customerId,
            @Valid @RequestBody CustomerRequest request) {

        return ResponseEntity.ok(
                customerService.updateCustomer(
                        customerId,
                        request
                )
        );
    }

    @DeleteMapping("/{customerId}")
    public ResponseEntity<Void> deleteCustomer(
            @PathVariable Long customerId) {

        customerService.deleteCustomer(customerId);

        return ResponseEntity.noContent().build();
    }
}