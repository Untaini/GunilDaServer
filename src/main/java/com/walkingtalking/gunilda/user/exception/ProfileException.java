package com.walkingtalking.gunilda.user.exception;

import com.walkingtalking.gunilda.base.BaseException;
import com.walkingtalking.gunilda.base.BaseExceptionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ProfileException extends BaseException {

    private BaseExceptionType exceptionType;
}
