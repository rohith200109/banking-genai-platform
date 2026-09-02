package com.bank.ai.accountservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccountNotFoundException.class)
    public ProblemDetail handleAccountNotFound(
            AccountNotFoundException exception) {

        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(
                        HttpStatus.NOT_FOUND,
                        exception.getMessage()
                );

        problemDetail.setTitle("Account Not Found");
        problemDetail.setProperty(
                "timestamp",
                LocalDateTime.now()
        );

        return problemDetail;
    }

    @ExceptionHandler(DuplicateAccountException.class)
    public ProblemDetail handleDuplicateAccount(
            DuplicateAccountException exception) {

        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(
                        HttpStatus.CONFLICT,
                        exception.getMessage()
                );

        problemDetail.setTitle("Duplicate Account");
        problemDetail.setProperty(
                "timestamp",
                LocalDateTime.now()
        );

        return problemDetail;
    }

    @ExceptionHandler(IllegalStateException.class)
    public ProblemDetail handleIllegalState(
            IllegalStateException exception) {

        ProblemDetail problemDetail =
                ProblemDetail.forStatusAndDetail(
                        HttpStatus.CONFLICT,
                        exception.getMessage()
                );

        problemDetail.setTitle("Invalid Account Operation");
        problemDetail.setProperty(
                "timestamp",
                LocalDateTime.now()
        );

        return problemDetail;
    }

    @ExceptionHandler(
        org.springframework.web.bind.MethodArgumentNotValidException.class
)
public ProblemDetail handleValidation(
        org.springframework.web.bind.MethodArgumentNotValidException exception) {

    ProblemDetail problemDetail =
            ProblemDetail.forStatusAndDetail(
                    HttpStatus.BAD_REQUEST,
                    "Request validation failed"
            );

    var errors = new java.util.HashMap<String, String>();

    exception.getBindingResult()
            .getFieldErrors()
            .forEach(error ->
                    errors.put(
                            error.getField(),
                            error.getDefaultMessage()
                    )
            );

    problemDetail.setTitle("Validation Error");
    problemDetail.setProperty("timestamp", LocalDateTime.now());
    problemDetail.setProperty("errors", errors);

    return problemDetail;
}
}