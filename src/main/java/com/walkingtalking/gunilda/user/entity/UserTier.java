package com.walkingtalking.gunilda.user.entity;

import com.walkingtalking.gunilda.user.type.TierType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTier {

    @Id
    private Long userId;

    @Column(nullable = false)
    private TierType tier;

    @Column(nullable = false)
    private Integer exp;

    @Column(nullable = false)
    private Date latestExpUpDate;

    public static UserTier from(Long userId) {
        return UserTier.builder()
                .userId(userId)
                .tier(TierType.BEGINNER)
                .exp(0)
                .latestExpUpDate(Date.valueOf(LocalDate.now().minusDays(1L)))
                .build();
    }

    public void expUp(Date date) {
        //같은 날에는 업데이트되지 않도록 함
        if (latestExpUpDate.before(date)) {
            this.exp += 1;

            if (!this.tier.isHighestTier() && exp >= 10) {
                this.tier = TierType.getNextTier(this.tier);
                this.exp = 0;
            }

            this.latestExpUpDate = date;
        }
    }

}
