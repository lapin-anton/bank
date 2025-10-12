package ru.yandex.practicum.bank.ui.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.yandex.practicum.bank.ui.validator.AgeValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Constraint(validatedBy = AgeValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAge {

    String message() default "Возраст должен быть от 18 до 100 лет";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
