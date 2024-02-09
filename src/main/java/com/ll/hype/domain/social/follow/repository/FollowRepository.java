package com.ll.hype.domain.social.follow.repository;

import com.ll.hype.domain.social.follow.entity.Follow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FollowRepository extends JpaRepository<Follow, Long> {

    @Modifying
    @Query("delete from Follow f where f.toMemberId = :toMemberId and f.fromMember.id = :fromMemberId")
    void deleteByToMemberIdAndFromMemberId(@Param("toMemberId") Long toMemberId, @Param("fromMemberId") Long fromMemberId);


    Long countByToMemberId(Long toMemberId);

    boolean existsByToMemberIdAndFromMemberId(Long toMemberId, Long fromMemberId);
}
