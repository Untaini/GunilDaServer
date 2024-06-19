package com.walkingtalking.gunilda.exercise.entity;

import com.walkingtalking.gunilda.exercise.dto.StatusDTO;
import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Date;

@Entity
@Getter
public class ExerciseStatus {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Date date;

    @Column(nullable = false)
    private Integer stride;

    @Column(nullable = false)
    private Double speed;

    @Column(nullable = false)
    private Integer step;

    @Column(nullable = false)
    private Integer numberOfExercise;

    public static ExerciseStatus emptyStatus(Long userId, Date date) {
        ExerciseStatus status = new ExerciseStatus();

        status.userId = userId;
        status.date = date;
        status.stride = 0;
        status.speed = 0.0;
        status.step = 0;
        status.numberOfExercise = 0;

        return status;
    }

    public Boolean isGoalAchieve(ExerciseGoal goal) {
        return this.stride >= goal.getStride()
                && this.speed >= goal.getSpeed()
                && this.step >= goal.getStep();
    }

    public void updateStatus(StatusDTO status) {
        //stride와 speed는 평균을, step은 총합을 계산함
        Integer sumOfStride = this.stride * this.numberOfExercise + status.stride() * status.dataCount();
        Double sumOfSpeed = this.speed * this.numberOfExercise + status.speed() * status.dataCount();

        this.numberOfExercise += status.dataCount();

        //운동한 데이터의 개수가 없다면 stride와 speed는 업데이트하지 않음
        if (this.numberOfExercise != 0) {
            this.stride = sumOfStride / this.numberOfExercise;
            this.speed = sumOfSpeed / this.numberOfExercise;
        }
        this.step += status.step();
    }

}
