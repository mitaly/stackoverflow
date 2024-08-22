package com.craft.stackoverflow.exception;

import com.craft.stackoverflow.model.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Autowired
    private MessageSource messageSource;
    @ExceptionHandler(AppException.class)
    public ResponseEntity<ErrorResponse> handleAppException(AppException ex) {
        String message = messageSource.getMessage(ex.getMessage(),
                ex.getParams(), LocaleContextHolder.getLocale());

        ErrorResponse errorResponse = new ErrorResponse(
                ex.getHttpStatusCode(),
                message,
                LocalDateTime.now()
        );
        return ResponseEntity.status(ex.getHttpStatusCode()).body(errorResponse);
    }
}
