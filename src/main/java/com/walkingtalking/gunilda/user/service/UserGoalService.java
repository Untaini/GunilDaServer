package com.walkingtalking.gunilda.user.service;

import com.walkingtalking.gunilda.user.dto.GoalDTO;

public interface UserGoalService {
    GoalDTO.Response getStatusAndGoal(GoalDTO.Command command);
}
