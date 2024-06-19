package com.walkingtalking.gunilda.user.repository;

import com.walkingtalking.gunilda.user.entity.User;
import com.walkingtalking.gunilda.user.type.AgeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(Long userId);
    Boolean existsByNickname(String nickname);

    @Query("SELECT u.age FROM User u WHERE u.userId = :userId")
    AgeType findAgeByUserId(Long userId);

    @Query("SELECT u.userId FROM User u WHERE u.age = :age")
    List<Long> findAllUserIdByAge(AgeType age);

}
