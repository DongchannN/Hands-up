package com.back.handsUp.repository.board;

import com.back.handsUp.domain.board.Board;
import com.back.handsUp.domain.board.BoardTagEntity;
import com.back.handsUp.domain.board.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardTagRepository extends JpaRepository<BoardTagEntity, Long> {
    List<BoardTagEntity> findAllByBoardIdx(Board boardIdx);
    Optional<BoardTagEntity> findByBoardIdxAndTagIdx(Board boardIdx, TagEntity tagIdx);
}
