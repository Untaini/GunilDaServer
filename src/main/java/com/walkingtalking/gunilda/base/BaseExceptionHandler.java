package com.walkingtalking.gunilda.base;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BaseExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseExceptionDTO> handleException(BaseException exception) {
        return ResponseEntity
                .status(exception.getExceptionType().getHttpStatus())
                .body(BaseExceptionDTO.from(exception));
    }

}
