package com.walkingtalking.gunilda.user.dto;

import com.walkingtalking.gunilda.user.entity.User;
import com.walkingtalking.gunilda.user.type.AgeType;
import com.walkingtalking.gunilda.user.type.GenderType;
import lombok.Builder;

public class UserProfileDTO {

    @Builder
    public record ChangeProfileRequest(String gender, String age) {
        public ChangeProfileCommand toCommand(Long userId) {
            return ChangeProfileCommand.builder()
                    .userId(userId)
                    .gender(GenderType.find(gender))
                    .age(AgeType.find(age))
                    .build();
        }
    }

    @Builder
    public record ChangeProfileCommand(Long userId, GenderType gender, AgeType age) {

    }

    @Builder
    public record ChangeProfileResponse(GenderType gender, AgeType age) {
        public static ChangeProfileResponse from(User user) {
            return ChangeProfileResponse.builder()
                    .gender(user.getGender())
                    .age(user.getAge())
                    .build();
        }
    }

    @Builder
    public record ChangeNicknameRequest(String nickname) {
        public ChangeNicknameCommand toCommand(Long userId) {
            return ChangeNicknameCommand.builder()
                    .userId(userId)
                    .nickname(nickname)
                    .build();
        }
    }

    @Builder
    public record ChangeNicknameCommand(Long userId, String nickname) {

    }

    @Builder
    public record ChangeNicknameResponse(String nickname) {
        public static ChangeNicknameResponse from(User user) {
            return ChangeNicknameResponse.builder()
                    .nickname(user.getNickname())
                    .build();
        }
    }

    @Builder
    public record CreateProfileRequest(String gender, String age, String nickname) {
        public CreateProfileCommand toCommand(Long userId) {
            return CreateProfileCommand.builder()
                    .userId(userId)
                    .gender(GenderType.find(gender))
                    .age(AgeType.find(age))
                    .nickname(nickname)
                    .build();
        }
    }

    @Builder
    public record CreateProfileCommand(Long userId, GenderType gender, AgeType age, String nickname) {
        public ChangeProfileCommand toProfileCommand() {
            return ChangeProfileCommand.builder()
                    .userId(userId)
                    .gender(gender)
                    .age(age)
                    .build();
        }

        public ChangeNicknameCommand toNicknameCommand() {
            return ChangeNicknameCommand.builder()
                    .userId(userId)
                    .nickname(nickname)
                    .build();
        }
    }


    @Builder
    public record CreateProfileResponse(GenderType gender, AgeType age, String nickname) {
        public static CreateProfileResponse from(ChangeProfileResponse profileResponse, ChangeNicknameResponse nicknameResponse) {
            return CreateProfileResponse.builder()
                    .gender(profileResponse.gender)
                    .age(profileResponse.age)
                    .nickname(nicknameResponse.nickname)
                    .build();
        }
    }
}
