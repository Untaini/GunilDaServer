package com.walkingtalking.gunilda.exercise.service.impl;

import com.walkingtalking.gunilda.exercise.dto.StatusUpdateDTO;
import com.walkingtalking.gunilda.exercise.entity.ExerciseGoal;
import com.walkingtalking.gunilda.exercise.entity.ExerciseStatus;
import com.walkingtalking.gunilda.exercise.exception.ExerciseException;
import com.walkingtalking.gunilda.exercise.exception.type.ExerciseExceptionType;
import com.walkingtalking.gunilda.exercise.repository.ExerciseGoalRepository;
import com.walkingtalking.gunilda.exercise.repository.ExerciseStatusRepository;
import com.walkingtalking.gunilda.exercise.service.ExerciseStatusUpdateService;
import com.walkingtalking.gunilda.user.entity.UserTier;
import com.walkingtalking.gunilda.user.exception.ProfileException;
import com.walkingtalking.gunilda.user.exception.type.ProfileExceptionType;
import com.walkingtalking.gunilda.user.repository.UserRepository;
import com.walkingtalking.gunilda.user.repository.UserTierRepository;
import com.walkingtalking.gunilda.user.type.AgeType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class ExerciseStatusUpdateServiceImpl implements ExerciseStatusUpdateService {

    private final UserRepository userRepository;
    private final UserTierRepository tierRepository;
    private final ExerciseGoalRepository goalRepository;
    private final ExerciseStatusRepository statusRepository;

    @Override
    @Transactional
    public StatusUpdateDTO.Response updateStatus(StatusUpdateDTO.Command command) {
        AgeType age = userRepository.findAgeByUserId(command.userId());

        //age 데이터가 제대로 설정되지 않았다면 예외 처리
        if (age.equals(AgeType.UNKNOWN)) {
            throw new ProfileException(ProfileExceptionType.NOT_PREPARED);
        }

        UserTier tier = tierRepository.getReferenceById(command.userId());

        //목표 데이터를 가져오기 위해 age와 tier로 pk 생성
        ExerciseGoal.PK goalPk = ExerciseGoal.PK.builder()
                .age(age)
                .tier(tier.getTier())
                .build();

        ExerciseGoal goal = goalRepository.getReferenceById(goalPk);

        //현재 유저의 운동 달성 데이터를 가져오기 위해 오늘의 날짜를 가져옴
        Date today = Date.valueOf(LocalDate.now());

        //DB에 저장된 status를 가져오고 만약 저장된 status가 없다면 비어 있는 status를 생성함
        ExerciseStatus currentStatus = statusRepository.findByUserIdAndDate(command.userId(), today)
                .orElse(ExerciseStatus.emptyStatus(command.userId(), today));

        //만약 오늘의 목표를 달성하지 못 했다면 status를 업데이트 함
        if (!currentStatus.isGoalAchieve(goal)) {
            try {
                currentStatus.updateStatus(command.status());

                statusRepository.save(currentStatus);

            } catch (Exception e) {
                //status를 제대로 업데이트를 할 수 없다면 request 필드가 잘못된 것임
                throw new ExerciseException(ExerciseExceptionType.EXERCISE_CANNOT_SAVE);
            }
        }

        //업데이트 직후에 목표를 달성했을 떄에도 실행이 되도록 변경
        if (currentStatus.isGoalAchieve(goal)) {
            //같은 날짜인 경우에는 exp가 오르지 않음
            tier.expUp(today);

            tierRepository.save(tier);
        }

        return StatusUpdateDTO.Response.builder()
                .build();
    }
}
