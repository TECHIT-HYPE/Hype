package com.ll.hype.domain.social.likes.repository;

import com.ll.hype.domain.social.likes.entity.Likes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikesRepository extends JpaRepository<Likes, Long> {
    @Modifying
    @Query(value = "INSERT INTO likes(social_Id, member_Id, create_Date, modify_date) VALUES(:socialId, :memberId, now(), now())", nativeQuery = true)
    void insertLikes(@Param("socialId") Long socialId, @Param("memberId") Long memberId);

    void deleteBySocialIdAndMemberId(Long socialId, Long memberId);

    // socialId에 해당하는 좋아요 수를 반환하는 메서드
    long countBySocialId(Long socialId);

    // socialId와 memberId에 해당하는 좋아요가 존재하는지 확인하는 메서드
    boolean existsBySocialIdAndMemberId(Long socialId, Long memberId);
}
