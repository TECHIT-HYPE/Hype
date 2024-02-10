package com.ll.hype.domain.social.likes.entity;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.social.social.entity.Social;
import com.ll.hype.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name="likes_uk",
                        columnNames = {"socialId", "memberId"}
                )
        }
)
public class Likes extends BaseEntity {

    @JoinColumn(name="memberId")
    @ManyToOne
    private Member member;

    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name="socialId")
    @ManyToOne
    private Social social;


}
