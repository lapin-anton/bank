package ru.yandex.practicum.bank.ui.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.yandex.practicum.bank.ui.annotation.PasswordMatches;
import ru.yandex.practicum.bank.ui.dto.PasswordUserFormDto;
import ru.yandex.practicum.bank.ui.dto.UserFormDto;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, PasswordUserFormDto> {

    @Override
    public boolean isValid(PasswordUserFormDto form, ConstraintValidatorContext context) {
        if (form.getPassword() == null || form.getConfirmPassword() == null) {
            return false;
        }

        boolean matched = form.getPassword().equals(form.getConfirmPassword());

        if (!matched) {
            context.disableDefaultConstraintViolation();
            context
                    .buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate())
                    .addPropertyNode("confirmPassword")
                    .addConstraintViolation();
        }

        return matched;
    }
}
