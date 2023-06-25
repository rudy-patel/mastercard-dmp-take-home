package com.api.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Custom validator for validating card numbers.
 */
public class CardNumberValidator implements ConstraintValidator<CardNumber, Long> {

    private static final int MIN_CARD_NUMBER_LENGTH = 16;

    /**
     * Initializes the validator.
     *
     * @param constraintAnnotation The annotation instance for this constraint.
     */
    @Override
    public void initialize(CardNumber constraintAnnotation) {
        // No initialization required
    }

    /**
     * Validates the card number.
     *
     * @param cardNumber The card number to be validated.
     * @param context    The validation context.
     * @return {@code true} if the card number is valid, {@code false} otherwise.
     */
    @Override
    public boolean isValid(Long cardNumber, ConstraintValidatorContext context) {
        if (cardNumber == null) {
            return true;  // Null values are handled by @NotNull annotation
        }
        String cardNumberString = String.valueOf(cardNumber);
        return cardNumberString.length() >= MIN_CARD_NUMBER_LENGTH;
    }
}
