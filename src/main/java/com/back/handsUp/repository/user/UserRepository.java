package com.back.handsUp.repository.user;

import com.back.handsUp.domain.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>  {
    Optional<UserEntity> findByUserIdx(Long userIdx);
}
