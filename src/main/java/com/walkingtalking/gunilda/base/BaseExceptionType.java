package com.walkingtalking.gunilda.base;

import org.springframework.http.HttpStatus;

public interface BaseExceptionType {
    HttpStatus getHttpStatus();
    String getErrorCode();
    String getMessage();
}
