package com.ll.hype.domain.social.social.controller;

import com.ll.hype.domain.social.social.dto.SocialDetailResponse;
import com.ll.hype.domain.social.social.dto.SocialUpdateRequest;
import com.ll.hype.domain.social.social.dto.SocialUploadRequest;
import com.ll.hype.domain.social.social.service.SocialDetailService;
import com.ll.hype.domain.social.social.service.SocialUpdateService;
import com.ll.hype.domain.social.social.service.SocialUploadService;
import com.ll.hype.global.security.authentication.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/social/feed")
public class socialController {
    @Autowired
    private SocialUploadService socialUploadService;
    @Autowired
    private SocialDetailService socialDetailService;
    @Autowired
    private SocialUpdateService socialUpdateService;

    @GetMapping("")
    public String uploadFeed() {
        return "/domain/social/social/social/socialupload";
    }

    @GetMapping("/{id}")
    public String getFeedById(@PathVariable Long id, Model model, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        SocialDetailResponse socialDetailResponse = socialDetailService.findSocial(id, userPrincipal.getMember().getId());

        model.addAttribute("detailDto", socialDetailResponse);
        return "/domain/social/social/social/socialdetail";}

    @PostMapping("/upload")
    public String uploadSocial(@RequestParam("content") String content,
                               @AuthenticationPrincipal UserPrincipal userPrincipal,
                               @RequestParam(value = "files") List<MultipartFile> files) {

        SocialUploadRequest socialUploadRequest = new SocialUploadRequest().builder()
                .content(content)
                .build();

        String memberEmail = userPrincipal.getUsername();
        Long principalId = userPrincipal.getMember().getId();
        socialUploadService.upload(socialUploadRequest, files, memberEmail);

        return "redirect:/social/profile/%s".formatted(principalId);
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
                .build();
        socialUpdateRequest.setContent(socialDetailResponse.getContent());
        socialUpdateRequest.setPostImages(socialDetailResponse.getPostImages());

        // 모델에 추가
        model.addAttribute("socialUpdateRequest", socialUpdateRequest);

        return "domain/social/social/social/socialupdate"; // 수정 폼으로 이동
    }



    @PostMapping("/update/{id}")
    public String update(@PathVariable Long id,
                         @ModelAttribute("socialUpdateRequest") SocialUpdateRequest socialUpdateRequest,
                         @RequestParam("files") List<MultipartFile> files,
                         @AuthenticationPrincipal UserPrincipal userPrincipal) {

        // 사용자 정보 설정
        String memberEmail = userPrincipal.getUsername();
        Long principalId = userPrincipal.getMember().getId();

        // 수정 서비스 호출
        socialUpdateService.update(id, files, socialUpdateRequest, memberEmail);

        return "redirect:/social/profile/%s".formatted(principalId);
    }
}
