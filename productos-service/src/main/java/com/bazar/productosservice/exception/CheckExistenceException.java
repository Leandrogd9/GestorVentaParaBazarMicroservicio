package com.bazar.productosservice.exception;

public class CheckExistenceException extends RuntimeException{
    public CheckExistenceException(String message) {
        super(message);
    }
}
