package com.api.password_recovery.infra.exception;

public class UserNotConfirmedException extends RuntimeException{
    public UserNotConfirmedException(String menssage){
        super(menssage);
    }

}
