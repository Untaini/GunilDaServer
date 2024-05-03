package com.walkingtalking.gunilda.user.dto;

import com.walkingtalking.gunilda.user.type.SocialType;
import lombok.Builder;

public class SocialSignDTO {

    @Builder
    public record SignInRequest(String type, String id) {
        public SignInCommand toCommand() {
            return SignInCommand.builder()
                    .socialType(SocialType.find(type))
                    .id(id)
                    .build();
        }
    }

    @Builder
    public record SignInCommand(SocialType socialType, String id) {

    }

    @Builder
    public record SignInResponse(Long userId) {

    }
}
