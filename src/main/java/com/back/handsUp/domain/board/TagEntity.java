package com.back.handsUp.domain.board;

import com.back.handsUp.baseResponse.BaseEntity;
import lombok.*;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name = "tag")
@NoArgsConstructor
@DynamicInsert
public class TagEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagIdx;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "varchar(10) default 'ACTIVE'")
    private String status;

    @Builder
    public TagEntity(String name, String status) {
        this.name = name;
        this.status = status;
    }

    public void changeStatus (String newStatus) {
        this.status = newStatus;
    }
}
