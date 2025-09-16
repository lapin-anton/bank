package ru.yandex.practicum.bank.ui.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.yandex.practicum.bank.ui.annotation.ValidAge;

import java.time.LocalDate;
import java.time.Period;

public class AgeValidator implements ConstraintValidator<ValidAge, LocalDate> {

    private static final int MIN_AGE = 18;
    private static final int MAX_AGE = 100;

    @Override
    public boolean isValid(LocalDate birthDate, ConstraintValidatorContext constraintValidatorContext) {
        if (birthDate == null) {
            return false;
        }

        int age = Period.between(birthDate, LocalDate.now()).getYears();
        return age >= MIN_AGE && age <= MAX_AGE;
    }
}
