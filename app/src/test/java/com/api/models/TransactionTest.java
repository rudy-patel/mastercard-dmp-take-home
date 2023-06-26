package com.api.models;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void whenTransactionIsValid_shouldPassValidation() {
        Transaction transaction = new Transaction();
        transaction.setCardNum(1234567890123456L);
        transaction.setAmount(100.0);

        Set<ConstraintViolation<Transaction>> violations = validator.validate(transaction);

        assertTrue(violations.isEmpty());
    }

    @Test
    void whenCardNumberIsNull_shouldFailValidation() {
        Transaction transaction = new Transaction();
        transaction.setAmount(100.0);

        Set<ConstraintViolation<Transaction>> violations = validator.validate(transaction);

        assertFalse(violations.isEmpty());
        assertEquals("Card number is required", violations.iterator().next().getMessage());
    }

    @Test
    void whenTransactionAmountIsNull_shouldFailValidation() {
        Transaction transaction = new Transaction();
        transaction.setCardNum(1234567890123456L);

        Set<ConstraintViolation<Transaction>> violations = validator.validate(transaction);

        assertFalse(violations.isEmpty());
        assertEquals("Transaction amount is required", violations.iterator().next().getMessage());
    }
}
