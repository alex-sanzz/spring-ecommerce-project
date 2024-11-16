package com.example.ecommerce.config.middleware;

import com.example.ecommerce.exception.BadRequestException;
import com.example.ecommerce.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.example.ecommerce.dto.response.ErrorResponse;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class GenericExceptionHandler{
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(ResourceNotFoundException exception){
        logError(404, exception.getMessage());
        return ResponseEntity.status(404).body(ErrorResponse.builder().
                message(exception.getMessage()).
                timestamp(LocalDateTime.now()).
                build());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponse> handleBadRequestException(BadRequestException exception){
        logError(400, exception.getMessage());
        return ResponseEntity.status(400).body(ErrorResponse.
                builder().message(exception.getMessage()).
                timestamp(LocalDateTime.now()).build());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentException(MethodArgumentNotValidException exception){
        String errorMessage = exception.getBindingResult().getAllErrors().stream().map(DefaultMessageSourceResolvable::getDefaultMessage).collect(Collectors.joining(", "));
        errorMessage = errorMessage.substring(0, errorMessage.length() - 2);
        logError(400, errorMessage);

        return ResponseEntity.status(400).body(ErrorResponse.
                builder().message(errorMessage).
                timestamp(LocalDateTime.now()).build());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception exception){
        logError(500, exception.getMessage());
        return ResponseEntity.status(500).body(ErrorResponse.builder().
                message(exception.getMessage()).timestamp(LocalDateTime.now())
                .build());
    }

    private void logError(int statusCode, String message){
        log.error("Error status code {} : {}", statusCode, message);

    }
}