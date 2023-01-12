package com.back.handsUp.domain.board;


import com.back.handsUp.baseResponse.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Getter
@Table(name = "board_tag")
@NoArgsConstructor
@DynamicInsert
@IdClass(BoardTagId.class)
public class BoardTagEntity extends BaseEntity {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "boardIdx")
    private Board boardIdx;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tagIdx")
    private TagEntity tagIdx;

    @Column(columnDefinition = "varchar(10) default 'ACTIVE'")
    private String status;

    @Builder
    public BoardTagEntity(Board boardIdx, TagEntity tagIdx, String status) {
        this.boardIdx = boardIdx;
        this.tagIdx = tagIdx;
        this.status = status;
    }

    public void changeBoardTag(Board boardIdx, TagEntity tagIdx) {
        this.boardIdx = boardIdx;
        this.tagIdx = tagIdx;
    }

    public void changeStatus (String newStatus) {
        this.status = newStatus;
    }
}
