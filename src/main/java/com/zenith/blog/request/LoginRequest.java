package com.zenith.blog.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(
        @NotEmpty(message = "Email is required")
        @Email(message = "Email is not valid")
        String email,
        @NotEmpty(message = "Password is required")
        String password
) {
}
