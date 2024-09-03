package com.api.password_recovery.infra.exception;

public class UserRecoveryCodeNotValid extends RuntimeException{
    public UserRecoveryCodeNotValid(String message) {
        super(message);
    }
}
