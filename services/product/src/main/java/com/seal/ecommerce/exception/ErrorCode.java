package com.seal.ecommerce.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    PRODUCT_NOT_FOUND(1001, "Product not found", HttpStatus.NOT_FOUND),
    CATEGORY_NOT_FOUND(1002, "Category not found", HttpStatus.NOT_FOUND),
    SUB_CATEGORY_NOT_FOUND(1003, "Sub category not found", HttpStatus.NOT_FOUND),
    PRODUCT_ALREADY_EXISTS(1004, "Product already exists", HttpStatus.CONFLICT),
    CATEGORY_ALREADY_EXISTS(1005, "Category already exists", HttpStatus.CONFLICT),
    SUB_CATEGORY_ALREADY_EXISTS(1006, "Sub category already exists", HttpStatus.CONFLICT),
    INVENTORY_NOT_FOUND(1007, "Inventory not found",HttpStatus.NOT_FOUND),
    FILE_STORAGE_EXCEPTION(1008,"Could not initialize storage",HttpStatus.INTERNAL_SERVER_ERROR),
    STORAGE_FILE_NOT_FOUND(1009,"Storage file not found", HttpStatus.NOT_FOUND),
    FILE_ALREADY_EXISTS(1010, "File already exist", HttpStatus.NOT_FOUND),
    INVALID_FILE_TYPE(1011, "Invalid File Type", HttpStatus.BAD_REQUEST),
    PRODUCT_IMAGE_NOT_FOUND(1012, "Product image not found", HttpStatus.NOT_FOUND),;

    ErrorCode(int code, String message, HttpStatusCode statusCode) {
        this.code = code;
        this.message = message;
        this.statusCode = statusCode;
    }

    private final int code;
    private final String message;
    private final HttpStatusCode statusCode;
}
