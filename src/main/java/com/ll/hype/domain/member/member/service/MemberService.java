package com.ll.hype.domain.member.member.service;

import com.ll.hype.domain.member.member.dto.JoinRequest;
import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.member.member.entity.MemberRole;
import com.ll.hype.domain.member.member.repository.MemberRepository;
import com.ll.hype.domain.member.member.dto.ModifyRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void join(JoinRequest joinRequest) {
        Member member = JoinRequest.toEntity(joinRequest);
        member.changeToEncodedPassword(passwordEncoder.encode(member.getPassword()));
        member.updateRole(MemberRole.MEMBER);
        memberRepository.save(member);
    }

    public boolean confirmPassword(String password, String passwordConfirm) {
        return password.equals(passwordConfirm);
    }

    public boolean existsByEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    public boolean existsByNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    public boolean existsByPhoneNumber(String phoneNumber) {
        return memberRepository.existsByPhoneNumber(phoneNumber);
    }

    @Transactional
    public void modify(ModifyRequest modifyRequest, Member member) {
        member.modifyProfile(passwordEncoder.encode(modifyRequest.getPassword()),
                        modifyRequest.getNickname(),
                        modifyRequest.getPhoneNumber(),
                        modifyRequest.getShoesSize()
        );

        memberRepository.save(member);
    }
}
