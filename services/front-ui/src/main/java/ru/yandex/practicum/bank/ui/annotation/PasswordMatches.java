package ru.yandex.practicum.bank.ui.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.yandex.practicum.bank.ui.validator.PasswordMatchesValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordMatches {

    String message() default "Пароль и подтверждение не совпадают";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
