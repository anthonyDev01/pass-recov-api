package com.api.password_recovery.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequestDto(@NotBlank String name, @Email String email, @NotBlank String password) {
}
