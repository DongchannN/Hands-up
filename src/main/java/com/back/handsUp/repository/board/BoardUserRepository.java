package com.back.handsUp.repository.board;

import com.back.handsUp.domain.board.BoardUserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardUserRepository extends JpaRepository<BoardUserEntity, Long> {
}
