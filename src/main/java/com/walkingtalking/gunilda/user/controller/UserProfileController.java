package com.walkingtalking.gunilda.user.controller;

import com.walkingtalking.gunilda.user.dto.UserProfileDTO;
import com.walkingtalking.gunilda.user.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserProfileController {

    private final UserProfileService profileService;

    @PostMapping()
    public ResponseEntity<UserProfileDTO.CreateProfileResponse> createProfile(@RequestBody UserProfileDTO.CreateProfileRequest profileRequest) {
        Long userId = 0L; //TO-DO

        return ResponseEntity.ok(profileService.createProfile(profileRequest.toCommand(userId)));
    }

    @PatchMapping("/profile")
    public ResponseEntity<UserProfileDTO.ChangeProfileResponse> changeProfile(@RequestBody UserProfileDTO.ChangeProfileRequest profileRequest) {
        Long userId = 0L; //TO-DO

        return ResponseEntity.ok(profileService.changeProfile(profileRequest.toCommand(userId)));
    }

    @PatchMapping("/nickname")
    public ResponseEntity<UserProfileDTO.ChangeNicknameResponse> changeNickname(@RequestBody UserProfileDTO.ChangeNicknameRequest nicknameRequest) {
        Long userId = 0L; //TO-DL

        return ResponseEntity.ok(profileService.changeNickname(nicknameRequest.toCommand(userId)));
    }
}
