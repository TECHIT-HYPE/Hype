package com.ll.hype.domain.social.social.repository;

import com.ll.hype.domain.social.social.entity.Social;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SocialRepository extends JpaRepository<Social, Long> {
    List<Social> findByMemberIdOrderByCreateDateDesc(Long memberId);
}
