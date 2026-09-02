package com.bank.ai.accountservice.repository;

import com.bank.ai.accountservice.entity.Account;
import com.bank.ai.accountservice.entity.AccountStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Lock;
public interface AccountRepository
        extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountNumber(String accountNumber);

    boolean existsByAccountNumber(String accountNumber);

    boolean existsByCustomerIdAndAccountType(
            Long customerId,
            String accountType
    );

    boolean existsByAccountNumberAndAccountIdNot(
            String accountNumber,
            Long accountId
    );

    @Lock(LockModeType.PESSIMISTIC_WRITE)
@Query("""
       SELECT a
       FROM Account a
       WHERE a.accountId = :accountId
       """)
Optional<Account> findByIdForUpdate(
        @Param("accountId") Long accountId
);

}