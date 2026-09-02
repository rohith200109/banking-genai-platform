package com.bank.ai.accountservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bank.ai.accountservice.entity.ProcessedEvent;

public interface ProcessedEventRepository
        extends JpaRepository<ProcessedEvent, String> {

    boolean existsByEventId(String eventId);
}