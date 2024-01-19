package com.ll.hype.domain.member.member.service;

import com.ll.hype.domain.member.member.dto.JoinRequest;
import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.member.member.entity.MemberRole;
import com.ll.hype.domain.member.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void join(JoinRequest joinRequest) {
        Member member = JoinRequest.toEntity(joinRequest);
        member.updateStatus(MemberRole.MEMBER);
        memberRepository.save(member);
    }
}
