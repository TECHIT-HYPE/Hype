package com.ll.hype.domain.social.follow.service;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.member.member.repository.MemberRepository;
import com.ll.hype.domain.social.follow.entity.Follow;
import com.ll.hype.domain.social.follow.repository.FollowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class FollowService {

    @Autowired
    FollowRepository followRepository;

    @Autowired
    MemberRepository memberRepository;

    @Transactional
    public void saveFollow(Long toMemberId, Long fromMemberId) {
        Optional<Member> fromMemberOptional = memberRepository.findById(fromMemberId);

        try {
            Member fromMember = fromMemberOptional.get();
            Follow follow = new Follow(toMemberId, fromMember);

            followRepository.save(follow);
        } catch (NoSuchElementException e) {
            e.printStackTrace();
            throw e;
        }
    }
    @Transactional
    public void deleteFollow(Long toMemberId, Long fromMemberId) {
        followRepository.deleteByToMemberIdAndFromMemberId(toMemberId, fromMemberId);
    }
}
