package com.walkingtalking.gunilda.auth.exception.type;

import com.walkingtalking.gunilda.base.BaseExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum AuthExceptionType implements BaseExceptionType {
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "A000", "Access Token이 만료되었습니다."),
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "A001", "Refresh Token이 만료되었습니다."),
    DUPLICATE_SIGN_IN(HttpStatus.UNAUTHORIZED, "A002", "중복 로그인이 감지되었습니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "A003", "Token이 손상되었습니다.");

    HttpStatus httpStatus;
    String errorCode;
    String message;
}
