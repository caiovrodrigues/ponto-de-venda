package com.caio.pdv.services.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobalHandlerExceptions {

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessage> entityNotFound(EntityNotFoundException ex){
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(
                        Instant.now(),
                        HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND,
                        ex.getMessage()
                        ));
    }

    @ExceptionHandler(ProductEstoqueInsuficiente.class)
    @ResponseStatus(HttpStatus.PRECONDITION_FAILED)
    public ResponseEntity<ErrorMessage> entityNotFound(ProductEstoqueInsuficiente ex){
        return ResponseEntity
                .status(HttpStatus.PRECONDITION_FAILED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(
                        Instant.now(),
                        HttpStatus.PRECONDITION_FAILED.value(),
                        HttpStatus.PRECONDITION_FAILED,
                        ex.getMessage()
                ));
    }

    @ExceptionHandler(UserAlreadyExist.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorMessage> userAlreadyExist(UserAlreadyExist e){
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(
                        Instant.now(),
                        HttpStatus.CONFLICT.value(),
                        HttpStatus.CONFLICT,
                        e.getMessage()
                ));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public ResponseEntity<ErrorMessage> argumentsNotValid(MethodArgumentNotValidException e){
        List<Map<String, String>> errors = e.getFieldErrors().stream().map(field -> Map.of(field.getField(), field.getDefaultMessage())).toList();
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(
                        Instant.now(),
                        HttpStatus.UNPROCESSABLE_ENTITY.value(),
                        HttpStatus.UNPROCESSABLE_ENTITY,
                        "Campos inválidos",
                        errors
                ));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    public ResponseEntity<ErrorMessage> illegalArgumentException(IllegalArgumentException e){
        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(
                   Instant.now(),
                   HttpStatus.METHOD_NOT_ALLOWED.value(),
                   HttpStatus.METHOD_NOT_ALLOWED,
                   e.getMessage()
                ));
    }

    @ExceptionHandler(InsufficientAuthenticationException.class)
    @ResponseStatus(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED)
    public ResponseEntity<ErrorMessage> notAuthenticated(InsufficientAuthenticationException e){
        return ResponseEntity
                .status(HttpStatus.NETWORK_AUTHENTICATION_REQUIRED)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ErrorMessage(
                        Instant.now(),
                        HttpStatus.NETWORK_AUTHENTICATION_REQUIRED.value(),
                        HttpStatus.NETWORK_AUTHENTICATION_REQUIRED,
                        e.getMessage()
                ));
    }

}
