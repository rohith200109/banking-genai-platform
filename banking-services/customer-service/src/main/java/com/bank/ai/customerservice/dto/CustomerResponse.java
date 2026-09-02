package com.bank.ai.customerservice.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CustomerResponse(

        Long customerId,

        String firstName,

        String lastName,

        String email,

        String phoneNumber,

        LocalDate dateOfBirth,

        LocalDateTime createdAt,

        LocalDateTime updatedAt
) {
}