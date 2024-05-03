package com.walkingtalking.gunilda.user.service.impl;

import com.walkingtalking.gunilda.user.dto.SocialSignDTO;
import com.walkingtalking.gunilda.user.dto.UserSignDTO;
import com.walkingtalking.gunilda.user.entity.Social;
import com.walkingtalking.gunilda.user.repository.SocialRepository;
import com.walkingtalking.gunilda.user.service.SocialSignService;
import com.walkingtalking.gunilda.user.service.UserSignService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SocialSignServiceImpl implements SocialSignService {

    private final SocialRepository socialRepository;
    private final UserSignService userSignService;

    @Override
    @Transactional
    public SocialSignDTO.SignInResponse signIn(SocialSignDTO.SignInCommand signInCommand) {
        if (!socialRepository.existsBySocialTypeAndSocialId(signInCommand.socialType(), signInCommand.id())) {
            //등록되지 않은 소셜 정보라면 새로운 사용자를 생성함
            //소셜 로그인 시 아이디, 비밀번호 내용은 필요 없으므로 랜덤 값을 저장함
            UserSignDTO.SignUpRequest userSignUpRequest = UserSignDTO.SignUpRequest.builder()
                    .sid(UUID.randomUUID().toString())
                    .pw(UUID.randomUUID().toString())
                    .build();

            UserSignDTO.SignUpResponse userSignUpResponse = userSignService.signUp(userSignUpRequest.toCommand());

            //새로운 사용자를 바탕으로 소셜과 연동함
            Social social = Social.of(signInCommand.socialType(), signInCommand.id(), userSignUpResponse.userId());
            socialRepository.save(social);
        }

        Social social = socialRepository.findBySocialTypeAndSocialId(signInCommand.socialType(), signInCommand.id());

        return SocialSignDTO.SignInResponse.builder()
                .userId(social.getUserId())
                .build();
    }

    //회원탈퇴 시 Listener를 이용해서 소셜 정보도 같이 삭제되게 구현해야 함

}
