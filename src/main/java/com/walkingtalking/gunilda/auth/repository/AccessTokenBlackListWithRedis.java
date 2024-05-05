package com.walkingtalking.gunilda.auth.repository;

import com.walkingtalking.gunilda.auth.entity.AccessTokenBlackList;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccessTokenBlackListWithRedis extends CrudRepository<AccessTokenBlackList, String> {
}
