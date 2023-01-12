package com.back.handsUp.domain.board;

import com.back.handsUp.baseResponse.BaseEntity;
import com.back.handsUp.domain.user.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "board_user")
@NoArgsConstructor
@DynamicInsert
@IdClass(BoardUserId.class)
public class BoardUserEntity extends BaseEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardIdx")
    private Board boardIdx;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userIdx")
    private UserEntity userIdx;

    @Column(columnDefinition = "varchar(20) default 'WRITE'")
    private String status;

    @Builder
    public BoardUserEntity(Board boardIdx, UserEntity userIdx, String status) {
        this.boardIdx = boardIdx;
        this.userIdx = userIdx;
        this.status = status;
    }

    public void changeStatus (String newStatus) {
        this.status = newStatus;
    }
}
