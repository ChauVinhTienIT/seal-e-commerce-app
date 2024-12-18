package com.seal.ecommerce.exception;

import com.seal.ecommerce.dto.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<?>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        HashMap<String, String> errors = new HashMap<>();

        e.getBindingResult().getFieldErrors().forEach(
                error -> {
                    String fieldName = error.getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                }
        );

        return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(ApiResponse.builder()
                        .code(HttpStatus.BAD_REQUEST.value())
                        .message("Invalid input")
                        .result(errors)
                        .build());
    }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<?> handlingAppException(AppException exception) {
        ErrorCode errorCode = exception.getErrorCode();
        return ResponseEntity.status(errorCode.getStatusCode())
                .body(
                    ApiResponse
                            .builder()
                            .code(errorCode.getCode())
                            .message(errorCode.getMessage())
                            .result(null)
                            .build()
                );
    }
}