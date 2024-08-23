package com.craft.stackoverflow.exception;

import lombok.Data;

@Data
public class BusinessException extends RuntimeException {
    int httpStatusCode;
    Object[] params;
    public BusinessException(int httpStatusCode, String message, Object... params) {
        super(message);
        this.httpStatusCode = httpStatusCode;
        this.params = params;
    }
}
