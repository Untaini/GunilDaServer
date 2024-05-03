package com.walkingtalking.gunilda.user.repository;

import com.walkingtalking.gunilda.user.entity.Social;
import com.walkingtalking.gunilda.user.type.SocialType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SocialRepository extends JpaRepository<Social, Long> {

    boolean existsBySocialTypeAndSocialId(SocialType socialType, String socialId);
    Social findBySocialTypeAndSocialId(SocialType socialType, String socialId);

}
