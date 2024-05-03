package com.walkingtalking.gunilda.base;

public abstract class BaseException extends RuntimeException {
    public abstract BaseExceptionType getExceptionType();
}
