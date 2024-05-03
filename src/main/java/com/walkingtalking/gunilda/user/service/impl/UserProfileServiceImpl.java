package com.walkingtalking.gunilda.user.service.impl;

import com.walkingtalking.gunilda.user.dto.UserProfileDTO;
import com.walkingtalking.gunilda.user.entity.User;
import com.walkingtalking.gunilda.user.exception.ProfileException;
import com.walkingtalking.gunilda.user.exception.type.ProfileExceptionType;
import com.walkingtalking.gunilda.user.repository.UserRepository;
import com.walkingtalking.gunilda.user.service.UserProfileService;
import com.walkingtalking.gunilda.user.type.AgeType;
import com.walkingtalking.gunilda.user.type.GenderType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserRepository profileRepository;

    @Override
    @Transactional
    public UserProfileDTO.ChangeProfileResponse changeProfile(UserProfileDTO.ChangeProfileCommand profileCommand) {
        User user = profileRepository.findByUserId(profileCommand.userId())
                .orElseThrow(() -> new ProfileException(ProfileExceptionType.USER_NOT_FOUND));

        //클라이언트로부터 잘못된 데이터가 들어왔다면 exception 발생
        if (profileCommand.gender() == GenderType.UNKNOWN) {
            throw new ProfileException(ProfileExceptionType.MISMATCH_GENDER);
        }

        if (profileCommand.age() == AgeType.UNKNOWN) {
            throw new ProfileException(ProfileExceptionType.MISMATCH_AGE);
        }

        //유저 정보를 업데이트함
        user.setGender(profileCommand.gender());
        user.setAge(profileCommand.age());

        profileRepository.save(user);

        return UserProfileDTO.ChangeProfileResponse.from(user);
    }

    @Override
    @Transactional
    public UserProfileDTO.ChangeNicknameResponse changeNickname(UserProfileDTO.ChangeNicknameCommand nicknameCommand) {
        User user = profileRepository.findByUserId(nicknameCommand.userId())
                .orElseThrow(() -> new ProfileException(ProfileExceptionType.USER_NOT_FOUND));

        //중복된 닉네임이 존재한다면 exception 발생
        if (profileRepository.existsByNickname(nicknameCommand.nickname())) {
            throw new ProfileException(ProfileExceptionType.CONFLICT_NICKNAME);
        }

        //유저 정보를 업데이트함
        user.setNickname(nicknameCommand.nickname());

        profileRepository.save(user);

        return UserProfileDTO.ChangeNicknameResponse.from(user);
    }

    @Override
    @Transactional
    public UserProfileDTO.CreateProfileResponse createProfile(UserProfileDTO.CreateProfileCommand profileCommand) {

        //중복된 닉네임이 있을 수 있으므로 닉네임을 먼저 업데이트함
        UserProfileDTO.ChangeNicknameResponse nicknameResponse = changeNickname(profileCommand.toNicknameCommand());
        UserProfileDTO.ChangeProfileResponse profileResponse = changeProfile(profileCommand.toProfileCommand());

        return UserProfileDTO.CreateProfileResponse.from(profileResponse, nicknameResponse);
    }


}
