package com.api.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = CardNumberValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CardNumber {
    String message() default "Card number must be at least 16 digits long";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

