package com.api.validation;

import javax.validation.ConstraintValidatorContext;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class CardNumberValidatorTest {

    private CardNumberValidator validator;
    private ConstraintValidatorContext context;

    @BeforeEach
    public void setUp() {
        validator = new CardNumberValidator();
        context = Mockito.mock(ConstraintValidatorContext.class);
    }

    @Test
    public void testValidCardNumber() {
        Long validCardNumber = 1234567890123456L;
        boolean isValid = validator.isValid(validCardNumber, context);
        Assertions.assertTrue(isValid);
    }

    @Test
    public void testInvalidCardNumber() {
        Long invalidCardNumber = 12345L;
        boolean isValid = validator.isValid(invalidCardNumber, context);
        Assertions.assertFalse(isValid);
    }

    @Test
    public void testNullCardNumber() {
        Long nullCardNumber = null;
        boolean isValid = validator.isValid(nullCardNumber, context);
        Assertions.assertTrue(isValid);
    }
}
