package com.ll.hype.domain.social.socialcomment.repository;

import com.ll.hype.domain.social.socialcomment.entity.SocialComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SocialCommentRepository extends JpaRepository<SocialComment, Long> {
    List<SocialComment> findBySocialId(Long socialId);
}
