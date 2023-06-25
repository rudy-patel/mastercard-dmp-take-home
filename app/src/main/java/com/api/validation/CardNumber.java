package com.api.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Custom validation annotation for validating card numbers.
 */
@Documented
@Constraint(validatedBy = CardNumberValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CardNumber {

    /**
     * Returns the error message template.
     *
     * @return The error message template.
     */
    String message() default "Card number must be at least 16 digits long";

    /**
     * Returns the validation groups to which this constraint belongs.
     *
     * @return The validation groups.
     */
    Class<?>[] groups() default {};

    /**
     * Returns the payload associated with the constraint.
     *
     * @return The payload.
     */
    Class<? extends Payload>[] payload() default {};
}
