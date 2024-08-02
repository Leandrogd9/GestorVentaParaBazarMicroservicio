package com.bazar.authservice.exception;

public class LoginFailure extends RuntimeException{
    public LoginFailure(String message) {
        super(message);
    }
}
