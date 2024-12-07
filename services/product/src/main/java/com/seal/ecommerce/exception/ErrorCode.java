package com.seal.ecommerce.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    PRODUCT_NOT_FOUND(1001, "Product not found", HttpStatus.NOT_FOUND),
    CATEGORY_NOT_FOUND(1002, "Category not found", HttpStatus.NOT_FOUND),
    SUB_CATEGORY_NOT_FOUND(1003, "Sub category not found", HttpStatus.NOT_FOUND),
    PRODUCT_ALREADY_EXISTS(1004, "Product already exists", HttpStatus.BAD_REQUEST),
    CATEGORY_ALREADY_EXISTS(1005, "Category already exists", HttpStatus.BAD_REQUEST),
    SUB_CATEGORY_ALREADY_EXISTS(1006, "Sub category already exists", HttpStatus.BAD_REQUEST),
    ;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
