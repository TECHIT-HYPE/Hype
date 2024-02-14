package com.ll.hype.domain.social.likes.service;

import com.ll.hype.domain.social.likes.repository.LikesRepository;
import com.ll.hype.domain.social.social.entity.Social;
import com.ll.hype.domain.social.social.repository.SocialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LikesService {
    private final LikesRepository likesRepository;

    private final SocialRepository socialRepository;

    @Transactional
    public void saveLikes(Long socialId, Long memberId) {
        likesRepository.insertLikes(socialId, memberId);
        long likesCount = likesRepository.countBySocialId(socialId);
        // 좋아요 상태를 새로 설정
        boolean likesState = likesRepository.existsBySocialIdAndMemberId(socialId, memberId);

        // Social 엔티티 가져오기
        Social social = socialRepository.findById(socialId).orElse(null);
        social.updateLikesInfo(likesCount, likesState);
    }

    @Transactional
    public void deleteLikes(Long socialId, Long memberId) {
        likesRepository.deleteBySocialIdAndMemberId(socialId, memberId);
        long likesCount = likesRepository.countBySocialId(socialId);
        // 좋아요 상태를 새로 설정
        boolean likesState = likesRepository.existsBySocialIdAndMemberId(socialId, memberId);

        // Social 엔티티 가져오기
        Social social = socialRepository.findById(socialId).orElse(null);
        social.updateLikesInfo(likesCount, likesState);
    }
}
