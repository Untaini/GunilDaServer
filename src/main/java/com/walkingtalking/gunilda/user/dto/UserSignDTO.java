package com.walkingtalking.gunilda.user.dto;

import lombok.Builder;

public class UserSignDTO {

    @Builder
    public record SignUpRequest(String sid, String pw) {
        public SignUpCommand toCommand() {
            return SignUpCommand.builder()
                    .sid(sid)
                    .pwHash(pw) //나중에 반드시 hash로 변환하는 로직 넣을 것 TO-DO
                    .build();
        }
    }

    @Builder
    public record SignUpCommand(String sid, String pwHash) {

    }

    @Builder
    public record SignUpResponse(Long userId) {

    }

    @Builder
    public record SignInRequest() {

    }

    @Builder
    public record SignInCommand() {

    }

    @Builder
    public record SignInResponse() {

    }

    @Builder
    public record SignOutRequest() {

    }

    @Builder
    public record SignOutCommand() {

    }

    @Builder
    public record SignOutResponse() {

    }

}
