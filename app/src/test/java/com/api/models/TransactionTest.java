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
        // Arrange
        Transaction transaction = new Transaction();
        transaction.setCardNum(1234567890123456L);
        transaction.setAmount(100.0);

        // Act
        Set<ConstraintViolation<Transaction>> violations = validator.validate(transaction);

        // Assert
        assertTrue(violations.isEmpty());
    }

    @Test
    void whenCardNumberIsNull_shouldFailValidation() {
        // Arrange
        Transaction transaction = new Transaction();
        transaction.setAmount(100.0);

        // Act
        Set<ConstraintViolation<Transaction>> violations = validator.validate(transaction);

        // Assert
        assertFalse(violations.isEmpty());
        assertEquals("Card number is required", violations.iterator().next().getMessage());
    }

    @Test
    void whenTransactionAmountIsNull_shouldFailValidation() {
        // Arrange
        Transaction transaction = new Transaction();
        transaction.setCardNum(1234567890123456L);

        // Act
        Set<ConstraintViolation<Transaction>> violations = validator.validate(transaction);

        // Assert
        assertFalse(violations.isEmpty());
        assertEquals("Transaction amount is required", violations.iterator().next().getMessage());
    }
}
