package com.zenith.blog.validator;

import com.zenith.blog.repository.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CheckEmailExists implements ConstraintValidator<EmailNotExists, String> {
    private final UserRepository userRepository;

    public CheckEmailExists(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {

        return !userRepository.existsByEmail(email);

    }
}
