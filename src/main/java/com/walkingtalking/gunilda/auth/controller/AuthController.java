package com.walkingtalking.gunilda.auth.controller;

import com.walkingtalking.gunilda.auth.dto.JwtTokenDTO;
import com.walkingtalking.gunilda.auth.dto.SocialLoginResponseDTO;
import com.walkingtalking.gunilda.auth.provider.JwtProvider;
import com.walkingtalking.gunilda.user.dto.SocialSignDTO;
import com.walkingtalking.gunilda.user.service.SocialSignService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtProvider jwtProvider;
    private final SocialSignService socialSignService;

    @PostMapping("/login")
    public ResponseEntity<SocialLoginResponseDTO> socialLogin(@RequestBody SocialSignDTO.SignInRequest request) {
        SocialSignDTO.SignInResponse signInResponse = socialSignService.signIn(request.toCommand());

        JwtTokenDTO.GeneratingWithIdRequest jwtRequest = JwtTokenDTO.GeneratingWithIdRequest.builder()
                .userId(signInResponse.userId())
                .build();

        JwtTokenDTO.GeneratingResponse jwtResponse = jwtProvider.generateToken(jwtRequest);

        SocialLoginResponseDTO response = SocialLoginResponseDTO.builder()
                .accessToken(jwtResponse.accessToken())
                .refreshToken(jwtResponse.refreshToken())
                .needInitialization(signInResponse.needInitialization())
                .build();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtTokenDTO.GeneratingResponse> refresh(@RequestBody JwtTokenDTO.GeneratingWithRefreshTokenRequest request) {
        return ResponseEntity.ok(jwtProvider.generateToken(request));
    }
}
