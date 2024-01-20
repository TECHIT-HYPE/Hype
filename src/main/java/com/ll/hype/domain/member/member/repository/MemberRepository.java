package com.ll.hype.domain.member.member.repository;

import com.ll.hype.domain.member.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    boolean existsByEmail(String email);

    boolean existsByNickname(String nickname);

    boolean existsByPhoneNumber(String phoneNumber);
}
