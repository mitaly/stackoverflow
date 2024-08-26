package com.craft.stackoverflow.util;

import co.elastic.clients.elasticsearch.nodes.Http;
import com.craft.stackoverflow.exception.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ValidatorUtil {
    @Autowired
    private Validator validator;

    public boolean isValid(Object object) {
        Errors validationErrors = validator.validateObject(object);
        if (validationErrors.getFieldErrorCount() == 0) return true;

        Map<String, String> fieldVsErrorDesc = new HashMap<>();
        validationErrors.getFieldErrors().forEach(
                fieldError -> {
                    fieldVsErrorDesc.put(fieldError.getField(),
                            fieldError.getDefaultMessage());
                }
        );
        BusinessException businessException = new BusinessException(HttpStatus.BAD_REQUEST.value(), "validation.error");
        businessException.setAdditionalInfo(fieldVsErrorDesc);
        throw businessException;
    }
}
