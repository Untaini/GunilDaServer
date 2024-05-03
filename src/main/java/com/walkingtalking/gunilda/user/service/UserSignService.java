package com.walkingtalking.gunilda.user.service;

import com.walkingtalking.gunilda.user.dto.UserSignDTO;

public interface UserSignService {

    UserSignDTO.SignUpResponse signup(UserSignDTO.SignUpCommand signUpCommand);
    UserSignDTO.SignInResponse signin(UserSignDTO.SignInCommand signInCommand);
    UserSignDTO.SignOutResponse signout(UserSignDTO.SignOutCommand signOutCommand);

}
