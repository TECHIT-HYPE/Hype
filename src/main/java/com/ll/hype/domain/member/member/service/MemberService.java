package com.ll.hype.domain.member.member.service;

import com.ll.hype.domain.member.member.dto.JoinRequest;
import com.ll.hype.domain.member.member.dto.ModifyRequest;
import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.member.member.entity.MemberRole;
import com.ll.hype.domain.member.member.repository.MemberRepository;
import com.ll.hype.domain.order.buy.dto.response.BuyResponse;
import com.ll.hype.domain.order.buy.entity.Buy;
import com.ll.hype.domain.order.buy.repository.BuyRepository;
import com.ll.hype.global.s3.image.ImageType;
import com.ll.hype.global.s3.image.imagebridge.component.ImageBridgeComponent;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final ImageBridgeComponent imageBridgeComponent;
    private final BuyRepository buyRepository;

    @Transactional
    public void join(JoinRequest joinRequest, List<MultipartFile> files) {
        Member member = JoinRequest.toEntity(joinRequest);
        member.changeToEncodedPassword(passwordEncoder.encode(member.getPassword()));
        member.updateRole(MemberRole.MEMBER);
        memberRepository.save(member);
        if (!files.get(0).getOriginalFilename().isBlank()) {
            imageBridgeComponent.save(ImageType.MEMBER, member.getId(), files);
        }
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

    public boolean existsByPhoneNumber(Long phoneNumber) {
        return memberRepository.existsByPhoneNumber(phoneNumber);
    }

    @Transactional
    public void modify(ModifyRequest modifyRequest, Member member, List<MultipartFile> files) {
        member.modifyProfile(passwordEncoder.encode(modifyRequest.getPassword()),
                modifyRequest.getNickname(),
                modifyRequest.getPhoneNumber(),
                modifyRequest.getShoesSize()
        );

        memberRepository.save(member);

        // 프로필 사진 삭제를 체크하면 동작
        if (modifyRequest.isRemovePhoto()) {
            imageBridgeComponent.delete(ImageType.MEMBER, member.getId());
            return;
        }

        // 프로필 사진 삭제를 체크하지 않고 파일을 등록한다면 동작
        if (!files.get(0).getOriginalFilename().isBlank()) {
            List<String> imageFullPaths = imageBridgeComponent.findAllFullPath(ImageType.MEMBER, member.getId());

            if (!imageFullPaths.isEmpty()) {
                imageBridgeComponent.delete(ImageType.MEMBER, member.getId());
            }

            imageBridgeComponent.save(ImageType.MEMBER, member.getId(), files);
        }
    }

    public List<String> getProfilePhoto(Long memberId) {
        return imageBridgeComponent.findAllFullPath(ImageType.MEMBER, memberId);
    }

    public List<BuyResponse> findMyBuyHistory(Member member) {
        List<Buy> findByBuyMemberAll = buyRepository.findByMember(member);
        List<BuyResponse> buyResponses = new ArrayList<>();

        for (Buy buy : findByBuyMemberAll) {
            List<String> fullPath = imageBridgeComponent.findOneFullPath(ImageType.SHOES, buy.getShoes().getId());
            BuyResponse buyResponse = BuyResponse.of(buy, fullPath);
            buyResponses.add(buyResponse);
        }

        return buyResponses;
    }
}
