package com.bazar.productosservice.controller;

import com.bazar.productosservice.dto.CustomErrorResponse;
import com.bazar.productosservice.exception.CheckExistenceException;
import com.bazar.productosservice.exception.RequestException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ControllerAdvice {
    @ExceptionHandler(DataAccessException.class)
    public ResponseEntity<CustomErrorResponse> runtimeExceptionHandler(DataAccessException ex, HttpServletRequest request){
        CustomErrorResponse error = CustomErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error("Internal Server Error")
                .message("No se puede acceder a la base de datos: "+ex.getMessage())
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<CustomErrorResponse> runtimeExceptionHandler(HttpMessageNotReadableException ex, HttpServletRequest request){
        CustomErrorResponse error = CustomErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error("Bad Request")
                .message("Error al deserializar el JSON: " + ex.getMessage())
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(RequestException.class)
    public ResponseEntity runtimeExceptionHandler(RequestException ex){
        return new ResponseEntity<>(ex.getMessagesList(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CheckExistenceException.class)
    public ResponseEntity<CustomErrorResponse> runtimeExceptionHandler(CheckExistenceException ex, HttpServletRequest request){
        CustomErrorResponse error = CustomErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.NOT_FOUND.value())
                .error("Not Found")
                .message(ex.getMessage())
                .path(request.getRequestURI())
                .build();
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
}
