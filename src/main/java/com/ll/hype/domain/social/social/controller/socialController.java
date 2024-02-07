package com.ll.hype.domain.social.social.controller;

import com.ll.hype.domain.social.social.dto.SocialDetailResponse;
import com.ll.hype.domain.social.social.dto.SocialUploadRequest;
import com.ll.hype.domain.social.social.service.SocialDetailService;
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

    @GetMapping("")
    public String uploadFeed() {
        return "/domain/social/social/social/socialupload";
    }

    @GetMapping("/{id}")
    public String getFeedById(@PathVariable Long id, Model model, UserPrincipal userPrincipal) {
        SocialDetailResponse socialDetailResponse = socialDetailService.findSocial(id);

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


}
