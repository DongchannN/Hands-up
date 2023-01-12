package com.back.handsUp.domain.board;

import com.back.handsUp.baseResponse.BaseEntity;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Setter
@Getter
@Table(name = "board")
@NoArgsConstructor
@DynamicInsert
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public long boardIdx;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(nullable = true, length= 100)
    private String location;

    @Column(nullable = false, length= 10)
    private String indicateLocation;

    @Column(nullable = false)
    private int messageDuration;

    @CreationTimestamp
    @Column(updatable = false)
    public LocalDateTime createdAt;

    @UpdateTimestamp
    public LocalDateTime updatedAt;

    @Column(columnDefinition = "varchar(10) default 'ACTIVE'")
    private String status;

    @Builder
    public Board(String content, String location, String indicateLocation, int messageDuration, String status) {
        this.content = content;
        this.location = location;
        this.indicateLocation = indicateLocation;
        this.messageDuration = messageDuration;
        this.status = status;
    }

    public void changeBoard(String content, String location, String indicateLocation, int messageDuration) {
        this.content = content;
        this.location = location;
        this.indicateLocation = indicateLocation;
        this.messageDuration = messageDuration;
    }

    public void changeStatus (String newStatus) {
        this.status = newStatus;
    }
}

