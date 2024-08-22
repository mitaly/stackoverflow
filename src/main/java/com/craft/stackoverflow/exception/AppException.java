package com.craft.stackoverflow.exception;

import lombok.Data;

@Data
public class AppException extends RuntimeException {
    int httpStatusCode;
    Object[] params;
    public AppException(int httpStatusCode, String message, Object... params) {
        super(message);
        this.httpStatusCode = httpStatusCode;
        this.params = params;
    }
}
