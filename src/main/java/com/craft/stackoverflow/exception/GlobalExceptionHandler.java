package com.craft.stackoverflow.exception;

import com.craft.stackoverflow.model.ErrorResponse;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AccountStatusException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.nio.file.AccessDeniedException;
import java.security.SignatureException;
import java.time.LocalDateTime;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleAppException(BusinessException ex) {
        ex.printStackTrace();
        return generateGenericErrorResponse(ex.getHttpStatusCode(), ex.getMessage(), ex.getParams());
    }

    private ResponseEntity<ErrorResponse> generateGenericErrorResponse(int httpStatusCode, String errorMessage,
                                                                       Object... params) {
        String message = messageSource.getMessage(errorMessage,
                params, LocaleContextHolder.getLocale());

        ErrorResponse errorResponse = new ErrorResponse(
                httpStatusCode,
                message,
                LocalDateTime.now()
        );
        return ResponseEntity.status(httpStatusCode).body(errorResponse);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception ex) {
        ex.printStackTrace();
        if (ex instanceof BadCredentialsException) {
            return generateGenericErrorResponse(HttpStatus.BAD_REQUEST.value(), "username.password.incorrect");
        }
        if (ex instanceof AccountStatusException) {
            return generateGenericErrorResponse(HttpStatus.FORBIDDEN.value(), "account.locked");
        }
        if (ex instanceof AccessDeniedException) {
            return generateGenericErrorResponse(HttpStatus.FORBIDDEN.value(), "access.denied");
        }
        if (ex instanceof SignatureException) {
            return generateGenericErrorResponse(HttpStatus.FORBIDDEN.value(), "invalid.signature");
        }
        if (ex instanceof ExpiredJwtException) {
            return generateGenericErrorResponse(HttpStatus.FORBIDDEN.value(), "jwt.expired");
        }

        return generateGenericErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), "internal.server.error");
    }
}
