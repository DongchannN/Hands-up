package com.back.handsUp.service;

import com.back.handsUp.baseResponse.BaseException;
import com.back.handsUp.baseResponse.BaseResponseStatus;
import com.back.handsUp.domain.board.Board;
import com.back.handsUp.dto.board.BoardReq;
import com.back.handsUp.domain.board.BoardTagEntity;
import com.back.handsUp.domain.board.BoardUserEntity;
import com.back.handsUp.domain.board.TagEntity;
import com.back.handsUp.domain.user.UserEntity;
import com.back.handsUp.repository.board.BoardRepository;
import com.back.handsUp.repository.board.BoardTagRepository;
import com.back.handsUp.repository.board.BoardUserRepository;
import com.back.handsUp.repository.board.TagRepository;
import com.back.handsUp.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class BoardService {

    private final BoardRepository boardRepository;
    private final TagRepository tagRepository;
    private final BoardTagRepository boardTagRepository;
    private final UserRepository userRepository;
    private final BoardUserRepository boardUserRepository;


    public Board boardViewByIdx(long idx) throws BaseException {
        Optional<Board> optional = this.boardRepository.findByBoardIdx(idx);
        if(optional.isEmpty()){
            throw new BaseException(BaseResponseStatus.NON_EXIST_BOARDIDX);
        }
        return optional.get();
    }

    //Todo: 로그인 구현 후 userIdx->principal
    public void addBoard(Long userIdx, BoardReq.GetBoardInfo boardInfo) throws BaseException {
        if(boardInfo.getIndicateLocation().equals("true") && boardInfo.getLocation() == null){
            throw new BaseException(BaseResponseStatus.LOCATION_ERROR);
        }
        if(boardInfo.getMessageDuration()<1 || boardInfo.getMessageDuration()>48){
            throw new BaseException(BaseResponseStatus.MESSAGEDURATION_ERROR);
        }
        Board boardEntity = Board.builder()
                .content(boardInfo.getContent())
                .indicateLocation(boardInfo.getIndicateLocation())
                .location(boardInfo.getLocation())
                .messageDuration(boardInfo.getMessageDuration())
                .build();
        try{
            this.boardRepository.save(boardEntity);
            setTags(boardInfo, boardEntity);
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.DATABASE_INSERT_ERROR);
        }
        Optional<UserEntity> optional = this.userRepository.findByUserIdx(userIdx);
        if(optional.isEmpty()){
            throw new BaseException(BaseResponseStatus.NON_EXIST_USERIDX);
        }
        UserEntity userEntity = optional.get();
        BoardUserEntity boardUserEntity = BoardUserEntity.builder()
                .boardIdx(boardEntity)
                .userIdx(userEntity)
                .status("WRITE")
                .build();
        try{
            this.boardUserRepository.save(boardUserEntity);
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.DATABASE_INSERT_ERROR);
        }

    }

    //boardIdx->principal
    public void patchBoard(Long boardIdx, BoardReq.GetBoardInfo boardInfo) throws BaseException{
        if(boardInfo.getIndicateLocation().equals("true") && boardInfo.getLocation() == null){
            throw new BaseException(BaseResponseStatus.LOCATION_ERROR);
        }
        if(boardInfo.getMessageDuration()<1 || boardInfo.getMessageDuration()>48){
            throw new BaseException(BaseResponseStatus.MESSAGEDURATION_ERROR);
        }
        Optional<Board> optional = this.boardRepository.findByBoardIdx(boardIdx);
        if(optional.isEmpty()){
            throw new BaseException(BaseResponseStatus.NON_EXIST_BOARDIDX);
        }
        Board boardEntity = optional.get();
        boardEntity.changeBoard(boardInfo.getContent(), boardInfo.getLocation(), boardInfo.getIndicateLocation(), boardInfo.getMessageDuration());
        try{
            this.boardRepository.save(boardEntity);
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.DATABASE_INSERT_ERROR);
        }
        List<BoardTagEntity> boardTagEntityList = this.boardTagRepository.findAllByBoardIdx(boardEntity);
        for(BoardTagEntity boardTag : boardTagEntityList){
            boardTag.changeStatus("INACTIVE");
        }
        try{
            setTags(boardInfo, boardEntity);
        } catch (Exception e) {
            throw new BaseException(BaseResponseStatus.DATABASE_INSERT_ERROR);
        }
    }

    private void setTags(BoardReq.GetBoardInfo boardInfo, Board boardEntity) {
        String[] tagNameList = boardInfo.getName().split("\\s");
        for(String tmp: tagNameList){
            Optional<TagEntity> tagEntity = this.tagRepository.findByName(tmp);
            if(tagEntity.isEmpty()){
                TagEntity newTagEntity = TagEntity.builder()
                        .name(tmp)
                        .build();
                this.tagRepository.save(newTagEntity);
                Optional<BoardTagEntity> optional = this.boardTagRepository.findByBoardIdxAndTagIdx(boardEntity, newTagEntity);
                if(optional.isEmpty()){
                    BoardTagEntity boardTagEntity = BoardTagEntity.builder()
                            .boardIdx(boardEntity)
                            .tagIdx(newTagEntity)
                            .build();
                    this.boardTagRepository.save(boardTagEntity);
                } else{
                    BoardTagEntity boardTagEntity = optional.get();
                    boardTagEntity.changeStatus("ACTIVE");
                }

            } else {
                Optional<BoardTagEntity> optional = this.boardTagRepository.findByBoardIdxAndTagIdx(boardEntity, tagEntity.get());
                if(optional.isEmpty()){
                    BoardTagEntity boardTagEntity = BoardTagEntity.builder()
                            .boardIdx(boardEntity)
                            .tagIdx(tagEntity.get())
                            .build();
                    this.boardTagRepository.save(boardTagEntity);
                } else{
                    BoardTagEntity boardTagEntity = optional.get();
                    boardTagEntity.changeStatus("ACTIVE");
                }
            }
        }
    }
}
