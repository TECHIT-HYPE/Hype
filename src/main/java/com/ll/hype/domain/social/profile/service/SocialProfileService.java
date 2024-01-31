package com.ll.hype.domain.social.profile.service;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.member.member.repository.MemberRepository;
import com.ll.hype.domain.social.profile.dto.SocialProfileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SocialProfileService {
    @Autowired
    private final MemberRepository memberRepository;

    @Transactional
    public SocialProfileDto findById(Long id) {
        Optional<Member> memberOptional = memberRepository.findById(id);

        if (memberOptional.isPresent()) {
            Member member = memberOptional.get();
            SocialProfileDto socialProfileDto = new SocialProfileDto().entityToDto(member);

            return socialProfileDto;
        } else {
            // 처리할 로직이나 에러 핸들링을 추가할 수 있습니다.
            throw new NoSuchElementException("해당 id에 대한 Member를 찾을 수 없습니다.");
        }
    }
}
