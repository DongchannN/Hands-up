package com.back.handsUp.domain.user;

import com.back.handsUp.baseResponse.BaseEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.util.Date;

@Entity
@Setter
@Getter
@Table(name = "user")
@NoArgsConstructor
@DynamicInsert
public class UserEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userIdx;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String password;

    @Column(nullable = false, length = 21)
    private String nickname;

    @Column(nullable = false, length = 100)
    private Date nicknameUpdatedAt;

    @OneToOne
    @JoinColumn(name = "characterIdx")
    private CharacterEntity characterIdx;

    @OneToOne
    @JoinColumn(name = "schoolIdx")
    private SchoolEntity schoolIdx;

    @Column(columnDefinition = "varchar(20) default 'ACTIVE'")
    private String status;

    @Builder
    public UserEntity(String email, String password, String nickname, Date nicknameUpdatedAt, CharacterEntity characterIdx, SchoolEntity schoolIdx, String status) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.nicknameUpdatedAt = nicknameUpdatedAt;
        this.characterIdx = characterIdx;
        this.schoolIdx = schoolIdx;
        this.status = status;
    }

    public void changeStatus (String newStatus) {
        this.status = newStatus;
    }
}
