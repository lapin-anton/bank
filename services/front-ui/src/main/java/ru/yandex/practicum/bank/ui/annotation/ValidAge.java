package ru.yandex.practicum.bank.ui.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.yandex.practicum.bank.ui.validator.AgeValidator;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AgeValidator.class)
@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidAge {

    String message() default "Возраст должен быть от 18 до 100 лет";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
