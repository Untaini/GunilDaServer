package com.walkingtalking.gunilda.user.service.impl;

import com.walkingtalking.gunilda.exercise.entity.ExerciseGoal;
import com.walkingtalking.gunilda.exercise.entity.ExerciseStatus;
import com.walkingtalking.gunilda.exercise.repository.ExerciseGoalRepository;
import com.walkingtalking.gunilda.exercise.repository.ExerciseStatusRepository;
import com.walkingtalking.gunilda.user.dto.GoalDTO;
import com.walkingtalking.gunilda.user.entity.UserTier;
import com.walkingtalking.gunilda.user.repository.UserRepository;
import com.walkingtalking.gunilda.user.repository.UserTierRepository;
import com.walkingtalking.gunilda.user.service.UserGoalService;
import com.walkingtalking.gunilda.user.type.AgeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class UserGoalServiceImpl implements UserGoalService {

    private final ExerciseGoalRepository goalRepository;
    private final ExerciseStatusRepository statusRepository;
    private final UserTierRepository tierRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public GoalDTO.Response getStatusAndGoal(GoalDTO.Command command) {
        AgeType age = userRepository.findAgeByUserId(command.userId());
        UserTier tier = tierRepository.getReferenceById(command.userId());

        ExerciseGoal.PK goalPK = ExerciseGoal.PK.builder()
                .age(age)
                .tier(tier.getTier())
                .build();

        ExerciseGoal goal = goalRepository.getReferenceById(goalPK);
        ExerciseStatus status = statusRepository.getReferenceById(command.userId());

        return GoalDTO.Response.of(tier, status, goal);
    }

}
