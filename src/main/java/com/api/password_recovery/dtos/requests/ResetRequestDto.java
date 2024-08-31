package com.api.password_recovery.dtos.requests;

public record ResetRequestDto(String email, String resetCode, String newPassword) {
}
