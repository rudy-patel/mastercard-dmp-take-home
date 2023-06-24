package com.api.validation;

import javax.validation.ConstraintValidator;

import javax.validation.ConstraintValidatorContext;

public class CardNumberValidator implements ConstraintValidator<CardNumber, Long> {

    private static final int MIN_CARD_NUMBER_LENGTH = 16;

    @Override
    public boolean isValid(Long cardNumber, ConstraintValidatorContext context) {
        if (cardNumber == null) {
            return true;  // Null values are handled by @NotNull annotation
        }
        String cardNumberString = String.valueOf(cardNumber);
        return cardNumberString.length() >= MIN_CARD_NUMBER_LENGTH;
    }
}
