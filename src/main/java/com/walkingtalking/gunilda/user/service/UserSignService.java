package com.walkingtalking.gunilda.user.service;

import com.walkingtalking.gunilda.user.dto.UserSignDTO;

public interface UserSignService {

    UserSignDTO.SignUpResponse signUp(UserSignDTO.SignUpCommand signUpCommand);
    UserSignDTO.SignInResponse signIn(UserSignDTO.SignInCommand signInCommand);
    UserSignDTO.SignOutResponse signOut(UserSignDTO.SignOutCommand signOutCommand);

}
