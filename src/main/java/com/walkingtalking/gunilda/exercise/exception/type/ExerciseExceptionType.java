package com.walkingtalking.gunilda.exercise.exception.type;

import com.walkingtalking.gunilda.base.BaseExceptionType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ExerciseExceptionType implements BaseExceptionType {
    EXERCISE_NOT_FOUND(HttpStatus.NOT_FOUND, "E001", "올바르지 않은 ID입니다."),
    EXERCISE_NOT_ALLOWED(HttpStatus.BAD_REQUEST, "E002", "다른 사람의 운동 정보입니다."),
    INSUFFICIENT_PARAMETER(HttpStatus.BAD_REQUEST, "E003", "파라미터 정보가 올바르지 않습니다."),
    EXERCISE_CANNOT_SAVE(HttpStatus.BAD_REQUEST, "E004", "저장하려는 운동 데이터가 올바르지 않습니다.");

    final HttpStatus httpStatus;
    final String errorCode;
    final String message;

}
