package com.bazar.clienteservice.controller;

import com.bazar.clienteservice.dto.CustomErrorResponse;
import com.bazar.clienteservice.exception.RequestException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @ExceptionHandler(RequestException.class)
    public ResponseEntity runtimeExceptionHandler(RequestException ex){
        return new ResponseEntity<>(ex.getMessagesList(), HttpStatus.BAD_REQUEST);
    }
}
