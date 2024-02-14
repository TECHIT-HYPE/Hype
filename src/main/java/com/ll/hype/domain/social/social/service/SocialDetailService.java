package com.ll.hype.domain.social.social.service;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.domain.shoes.shoes.repository.ShoesRepository;
import com.ll.hype.domain.social.likes.repository.LikesRepository;
import com.ll.hype.domain.social.social.dto.SocialDetailResponse;
import com.ll.hype.domain.social.social.dto.SocialShoesRequest;
import com.ll.hype.domain.social.social.entity.Social;
import com.ll.hype.domain.social.social.entity.SocialShoes;
import com.ll.hype.domain.social.social.repository.SocialRepository;
import com.ll.hype.domain.social.social.repository.SocialShoesRepository;
import com.ll.hype.domain.social.socialcomment.dto.SocialCommentRequest;
import com.ll.hype.domain.social.socialcomment.entity.SocialComment;
import com.ll.hype.domain.social.socialcomment.repository.SocialCommentRepository;
import com.ll.hype.domain.social.socialcomment.service.SocialCommentService;
import com.ll.hype.global.s3.image.ImageType;
import com.ll.hype.global.s3.image.imagebridge.component.ImageBridgeComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class SocialDetailService {
    private final SocialRepository socialRepository;
    private final LikesRepository likesRepository;
    private final ImageBridgeComponent imageBridgeComponent;
    private final SocialCommentRepository socialCommentRepository;
    private final SocialShoesRepository socialShoesRepository;

    public SocialDetailResponse findSocial(Long socialId, Long principalId) {
        Social social = socialRepository.socialDetail(socialId, principalId);

        if (social != null) {
            Member member = social.getMember();
            String profileImage = imageBridgeComponent.findOneFullPath(ImageType.MEMBER, member.getId()).get(0);
            List<String> postImages = imageBridgeComponent.findAllFullPath(ImageType.SOCIAL, social.getId());
            List<SocialShoes> socialShoesList = socialShoesRepository.findAllBySocial_Id(socialId);
            List<SocialShoesRequest> socialShoesRequestList = new ArrayList<>();

            // 좋아요 정보 가져오기
            Long likesCount = likesRepository.countBySocialId(socialId); // 좋아요 수
            boolean likesState = likesRepository.existsBySocialIdAndMemberId(socialId, principalId); // 좋아요 상태

            // 댓글 목록 가져오기
            List<SocialComment> comments = socialCommentRepository.findBySocialId(socialId);
            List<SocialCommentRequest> commentRequests = new ArrayList<>();

            for (SocialComment comment : comments) {
                SocialCommentRequest request = new SocialCommentRequest(comment);
                commentRequests.add(request);
            }

            for (SocialShoes socialShoes : socialShoesList) {
                Long shoesId = socialShoes.getShoes().getId();
                String engName = socialShoes.getShoes().getEngName();

                String socialShoesImages = imageBridgeComponent.findOneFullPath(ImageType.SHOES, shoesId).get(0);
                socialShoesRequestList.add(new SocialShoesRequest(shoesId, socialShoesImages, engName));
            }

            // SocialDetailResponse 객체 생성 및 반환
            return SocialDetailResponse.builder()
                    .socialId(social.getId())
                    .member(member)
                    .content(social.getContent())
                    .profileImage(profileImage)
                    .postImages(postImages)
                    .createDate(social.getCreateDate())
                    .likesCount(likesCount)
                    .likesState(likesState)
                    .socialCommentRequestList(commentRequests)
                    .socialShoesRequestList(socialShoesRequestList)
                    .build();
        } else {
            throw new NoSuchElementException("해당 id에 대한 Social를 찾을 수 없습니다.");
        }
    }


    public void delete(Long socialId) {
        imageBridgeComponent.delete(ImageType.SOCIAL, socialId);
        socialRepository.deleteById(socialId);
    }

    public void update(SocialDetailResponse socialDetailResponse, List<MultipartFile> files) {
        Social social = toEntity(socialDetailResponse);

        socialRepository.save(social);

        imageBridgeComponent.save(ImageType.SOCIAL, social.getId(), files);
    }

    private Social toEntity(SocialDetailResponse socialDetailResponse) {
        return Social.builder()
                .id(socialDetailResponse.getSocialId())
                .member(socialDetailResponse.getMember())
                .content(socialDetailResponse.getContent())
                .build();
    }
}
