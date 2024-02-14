package com.ll.hype.domain.social.social.repository;

import com.ll.hype.domain.social.social.entity.Social;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SocialRepository extends JpaRepository<Social, Long> {
    List<Social> findByMemberIdOrderByCreateDateDesc(Long memberId);

    @Query(value="SELECT id, content, create_date, modify_date, member_id, \r\n"
            + "(Select COUNT(social_id) from likes where social_id = :socialId) likes_Count,\r\n"
            + "(Select EXISTS (select id from likes where member_Id = :principalId and social_Id = :socialId)) likes_State\r\n"
            + "FROM social where id = :socialId", nativeQuery = true)
    Social socialDetail(@Param("socialId") Long socialId, @Param("principalId") Long principalId);


}
