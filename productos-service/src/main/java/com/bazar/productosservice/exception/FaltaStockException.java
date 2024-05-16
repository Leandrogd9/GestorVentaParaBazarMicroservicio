package com.bazar.productosservice.exception;

public class FaltaStockException extends RuntimeException{
    public FaltaStockException(String message) {
        super(message);
    }
}
