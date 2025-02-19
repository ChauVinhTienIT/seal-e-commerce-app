package com.seal.ecommerce.exception;

import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    USER_NOT_FOUND(1001, "Product not found", HttpStatus.NOT_FOUND),
    ROLE_TITLE_NOT_FOUND(2001, "Role title not found", HttpStatus.NOT_FOUND ),
    TOKEN_NOT_FOUND(3001, "Token not found", HttpStatus.NOT_FOUND ),
    EMAIL_WAS_USED(1002, "Email was used", HttpStatus.CONFLICT);

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
