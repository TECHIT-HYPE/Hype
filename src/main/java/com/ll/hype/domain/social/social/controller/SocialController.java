package com.ll.hype.domain.social.social.controller;

import com.ll.hype.domain.shoes.search.service.ShoesSearchService;
import com.ll.hype.domain.shoes.shoes.dto.ShoesSearchResponse;
import com.ll.hype.domain.social.social.dto.SocialDetailResponse;
import com.ll.hype.domain.social.social.dto.SocialShoesRequest;
import com.ll.hype.domain.social.social.dto.SocialUpdateRequest;
import com.ll.hype.domain.social.social.dto.SocialUploadRequest;
import com.ll.hype.domain.social.social.entity.Social;
import com.ll.hype.domain.social.social.service.SocialDetailService;
import com.ll.hype.domain.social.social.service.SocialUpdateService;
import com.ll.hype.domain.social.social.service.SocialUploadService;
import com.ll.hype.domain.social.socialcomment.dto.SocialCommentRequest;
import com.ll.hype.global.s3.image.ImageType;
import com.ll.hype.global.s3.image.imagebridge.component.ImageBridgeComponent;
import com.ll.hype.global.security.authentication.UserPrincipal;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/social/feed")
public class SocialController {
    private final SocialUploadService socialUploadService;

    private final SocialDetailService socialDetailService;

    private final SocialUpdateService socialUpdateService;

    private final ShoesSearchService shoesSearchService;

    private final ImageBridgeComponent imageBridgeComponent;

    private static final Logger logger = LoggerFactory.getLogger(SocialController.class);


    @GetMapping("")
    public String uploadFeed() {
        return "/domain/social/social/social/socialupload";
    }

    @GetMapping("/{id}")
    public String getFeedById(@PathVariable Long id, Model model, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        SocialDetailResponse socialDetailResponse = socialDetailService.findSocial(id, userPrincipal.getMember().getId());

        model.addAttribute("detailDto", socialDetailResponse);
        model.addAttribute("comment", new SocialCommentRequest());
        return "/domain/social/social/social/socialdetail";
    }


    @PostMapping("/upload")
    public String uploadSocial(@RequestParam("content") String content,
                               @AuthenticationPrincipal UserPrincipal userPrincipal,
                               @RequestParam(value = "files") List<MultipartFile> files,
                               @RequestParam(value = "shoesId", required = false) List<Long> shoesIdList,
                               @RequestParam(value = "shoesName", required = false) List<String> shoesNameList,
                               Model model) {

        if (files == null) {
            throw new IllegalArgumentException("파일이 null입니다.");
        }

        SocialUploadRequest socialUploadRequest = new SocialUploadRequest().builder()
                .content(content)
                .build();


        String memberEmail = userPrincipal.getUsername();

        Social social = socialUploadService.upload(socialUploadRequest, files, memberEmail);
        if (shoesIdList != null && shoesNameList != null && !shoesIdList.isEmpty() && !shoesNameList.isEmpty()) {
            for (int i = 0; i < shoesIdList.size(); i++) {
                Long shoesId = shoesIdList.get(i);
                String shoesName = shoesNameList.get(i);
                String shoesImage = imageBridgeComponent.findOneFullPath(ImageType.SHOES, shoesId).get(0);
                SocialShoesRequest socialShoesRequest = SocialShoesRequest.builder()
                        .shoesId(shoesId)
                        .shoesName(shoesName)
                        .shoesImage(shoesImage)
                        .build();
                socialUploadService.tagUpload(socialShoesRequest, social.getId());
            }
        }

        return "redirect:/social/feed/%s".formatted(social.getId());
    }


    @GetMapping("/delete/{id}")
    public String deleteSocial(@PathVariable Long id,
                               @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long principalId = userPrincipal.getMember().getId();
        log.debug("deleteSocial 메서드 호출됨. id: {}, principalId: {}", id, principalId);
        socialDetailService.delete(id);
        return "redirect:/social/profile/%s".formatted(principalId);
    }

    @GetMapping("/update/{id}") // 수정 폼 표시
    public String updateSocialForm(@PathVariable Long id, Model model, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        // 기존의 내용을 가져와서 SocialUpdateRequest 객체에 설정
        SocialDetailResponse socialDetailResponse = socialDetailService.findSocial(id, userPrincipal.getMember().getId());
        SocialUpdateRequest socialUpdateRequest = SocialUpdateRequest.builder()
                .socialId(socialDetailResponse.getSocialId())
                .content(socialDetailResponse.getContent())
                .postImages(socialDetailResponse.getPostImages())
                .socialShoesRequestList(socialDetailResponse.getSocialShoesRequestList())
                .build();
        // 모델에 추가
        model.addAttribute("socialUpdateRequest", socialUpdateRequest);

        return "domain/social/social/social/socialupdate"; // 수정 폼으로 이동
    }


    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute("socialUpdateRequest") SocialUpdateRequest socialUpdateRequest,
                         @RequestParam("files") List<MultipartFile> files,
                         @AuthenticationPrincipal UserPrincipal userPrincipal,
                         @RequestParam(value = "shoesId", required = false) List<Long> shoesIdList,
                         @RequestParam(value = "shoesName", required = false) List<String> shoesNameList) {

        // 사용자 정보 설정
        String memberEmail = userPrincipal.getUsername();
        Long principalId = userPrincipal.getMember().getId();

        // 수정 서비스 호출
        socialUpdateService.update(id, files, socialUpdateRequest, memberEmail);

        return "redirect:/social/profile/%s".formatted(principalId);
    }

    @GetMapping("/tag/search")
    public String searchShoes() {
        return "/domain/social/social/social/socialshoessearch";
    }

    @PostMapping("/tag/search")
    public String showShoesList(@RequestParam(value = "keyword") String keyword, Model model) {
        ShoesSearchResponse data = shoesSearchService.findByKeyword(keyword);
        model.addAttribute("data", data);
        return "/domain/social/social/social/socialshoessearchlist";
    }
}
