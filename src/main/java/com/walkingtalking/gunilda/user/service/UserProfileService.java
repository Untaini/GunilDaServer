package com.walkingtalking.gunilda.user.service;

import com.walkingtalking.gunilda.user.dto.UserProfileDTO;

public interface UserProfileService {

    UserProfileDTO.ChangeProfileResponse changeProfile(UserProfileDTO.ChangeProfileCommand profileCommand);
    UserProfileDTO.ChangeNicknameResponse changeNickname(UserProfileDTO.ChangeNicknameCommand nicknameCommand);
    UserProfileDTO.CreateProfileResponse createProfile(UserProfileDTO.CreateProfileCommand profileCommand);
    Boolean needChangingProfile(Long userId);
}
