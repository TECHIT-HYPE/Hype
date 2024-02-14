package com.ll.hype.domain.social.follow.entity;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(
        uniqueConstraints = {
                @UniqueConstraint(
                        name="follow_uk",
                        columnNames = {"toMemberId", "fromMemberId"}
                )
        }
)
public class Follow extends BaseEntity {
    private Long toMemberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fromMemberId")
    private Member fromMember;
}
