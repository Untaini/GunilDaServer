package com.walkingtalking.gunilda.user.service.impl;

import com.walkingtalking.gunilda.user.dto.UserSignDTO;
import com.walkingtalking.gunilda.user.entity.User;
import com.walkingtalking.gunilda.user.repository.UserRepository;
import com.walkingtalking.gunilda.user.service.UserSignService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserSignServiceImpl implements UserSignService {

    private final UserRepository userRepository;

    @Override
    @Transactional
    public UserSignDTO.SignUpResponse signup(UserSignDTO.SignUpCommand signUpCommand) {
        //나중에 아이디, 비밀번호로 회원가입을 할 경우 아이디 예외 처리를 여기에 구현할 것

        User user = User.of(signUpCommand.sid(), signUpCommand.pwHash());

        userRepository.save(user);

        //유저의 기본 닉네임을 guest + 서비스 ID로 설정함
        user.setNickname("guest" + user.getUserId());

        userRepository.save(user);

        return UserSignDTO.SignUpResponse.builder()
                .userId(user.getUserId())
                .build();
    }

    //나중에 아이디, 비밀번호로 로그인하게 될 경우 아래 함수를 구현할 것
    @Override
    @Transactional
    public UserSignDTO.SignInResponse signin(UserSignDTO.SignInCommand signInCommand) {
        return null;
    }

    //나중에 회원탈퇴를 만들게 될 경우 아래 함수를 구현할 것
    @Override
    @Transactional
    public UserSignDTO.SignOutResponse signout(UserSignDTO.SignOutCommand signOutCommand) {
        return null;
    }
}
