package com.ll.hype.domain.social.social.controller;

import com.ll.hype.domain.social.social.dto.SocialUploadRequest;
import com.ll.hype.domain.social.social.service.SocialService;
import com.ll.hype.global.security.authentication.UserPrincipal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/social/feed")
public class socialController {
    @Autowired
    private SocialService socialService;

    @GetMapping("")
    public String getFeed() {
        return "/domain/social/social/social/socialupload";
    }

    @PostMapping("/upload")
    public String uploadSocial(@RequestParam("content") String content,
                               @AuthenticationPrincipal UserPrincipal userPrincipal,
                               @RequestParam(value = "files") List<MultipartFile> files) {

        SocialUploadRequest socialUploadRequest = new SocialUploadRequest().builder()
                .content(content)
                .build();

        String memberEmail = userPrincipal.getUsername();
        Long principalId = userPrincipal.getMember().getId();
        socialService.upload(socialUploadRequest, files, memberEmail);

        return "redirect:/social/profile/%s".formatted(principalId);
    }

}
