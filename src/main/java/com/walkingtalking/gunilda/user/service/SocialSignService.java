package com.walkingtalking.gunilda.user.service;

import com.walkingtalking.gunilda.user.dto.SocialSignDTO;

public interface SocialSignService {

    SocialSignDTO.SignInResponse signIn(SocialSignDTO.SignInCommand signInCommand);

}
