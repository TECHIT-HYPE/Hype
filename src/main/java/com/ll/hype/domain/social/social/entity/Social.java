package com.ll.hype.domain.social.social.entity;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.social.social.dto.SocialUpdateRequest;
import com.ll.hype.global.jpa.BaseEntity;
import com.ll.hype.domain.social.socialcomment.entity.SocialComment;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Social extends BaseEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String content;

    @NotNull
    @Column(name = "modify_date")
    @LastModifiedDate
    private LocalDateTime modifyDate;

    private Long likesCount;

    private boolean likesState;

    @OneToMany(mappedBy = "social", cascade = CascadeType.ALL)
    private List<SocialShoes> socialShoes = new ArrayList<>();

    @OneToMany(mappedBy = "social", cascade = CascadeType.ALL)
    private List<SocialComment> socialCommentsList = new ArrayList<>();

    public void updateSocial(String content) {
        this.content = content;
    }

    public void updateLikesInfo(Long likesCount, boolean likesState) {
        this.likesCount = likesCount;
        this.likesState = likesState;
        this.modifyDate = LocalDateTime.now(); // 좋아요 정보를 업데이트했으므로 수정 날짜도 업데이트
    }
}
