package com.walkingtalking.gunilda.user.controller;

import com.walkingtalking.gunilda.user.dto.GoalDTO;
import com.walkingtalking.gunilda.user.service.UserGoalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserGoalController {

    private final UserGoalService goalService;

    @GetMapping("/goal")
    public ResponseEntity<GoalDTO.Response> getGoal(@RequestAttribute Long userId) {

        //아직까지는 goal을 얻을 때 보낼 데이터가 없어서 Request를 생성해 사용함
        //만약 추후 Parameter를 넣는다면 @RequestParam을 이용해 받도록 변경해야 함
        GoalDTO.Request request = new GoalDTO.Request();

        return ResponseEntity.ok(goalService.getStatusAndGoal(request.toCommand(userId)));
    }

}
