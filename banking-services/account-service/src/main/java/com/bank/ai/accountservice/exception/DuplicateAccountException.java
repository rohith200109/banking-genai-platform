package com.bank.ai.accountservice.exception;

public class DuplicateAccountException
        extends RuntimeException {

    public DuplicateAccountException(String message) {
        super(message);
    }
}