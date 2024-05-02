package com.walkingtalking.gunilda.user.exception.type;

import com.walkingtalking.gunilda.base.BaseExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ProfileExceptionType implements BaseExceptionType {
    USER_NOT_FOUND(HttpStatus.BAD_REQUEST, "U000", "유저를 찾을 수 없습니다."),
    MISMATCH_GENDER(HttpStatus.BAD_REQUEST, "U001", "올바르지 않은 성별 데이터입니다."),
    MISMATCH_AGE(HttpStatus.BAD_REQUEST, "U002", "올바르지 않은 연령대 데이터입니다."),
    CONFLICT_NICKNAME(HttpStatus.CONFLICT, "U003", "중복된 닉네임입니다.");

    HttpStatus httpStatus;
    String errorCode;
    String message;
}
