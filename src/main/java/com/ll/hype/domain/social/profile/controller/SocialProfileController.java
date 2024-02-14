package com.ll.hype.domain.social.profile.controller;

import com.ll.hype.domain.member.member.dto.ModifyRequest;
import com.ll.hype.domain.social.profile.dto.SocialProfileResponse;
import com.ll.hype.domain.social.profile.service.SocialProfileService;
import com.ll.hype.domain.social.social.dto.SocialUpdateRequest;
import com.ll.hype.global.s3.image.imagebridge.component.ImageBridgeComponent;
import com.ll.hype.global.security.authentication.UserPrincipal;
import jakarta.validation.Valid;
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
@RequestMapping("/social/profile")
public class SocialProfileController {
    @Autowired
    private SocialProfileService socialProfileService;

    @Autowired
    private ImageBridgeComponent imageBridgeComponent;

    @GetMapping("")
    public String profileDefault(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        Long principalId = userPrincipal != null ? userPrincipal.getMember().getId() : null;
        return "redirect:/social/profile/%s".formatted(principalId);
    }

    @GetMapping("/{id}")
    public String getProfilePage(@PathVariable Long id, Model model, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        SocialProfileResponse socialProfileResponse = socialProfileService.findById(id, userPrincipal.getMember().getId());

        // 경준님 화이팅

        // principalId를 model에 추가
        model.addAttribute("principal", id);
        model.addAttribute("profileDto", socialProfileResponse);
        model.addAttribute("imageBridgeComponent", imageBridgeComponent);
        return "/domain/social/social/socialprofile/socialprofile";
    }
    @PostMapping("/{id}")
    public String updateProfileImage(@PathVariable Long id,
                                     @ModelAttribute("socialUpdateRequest") SocialUpdateRequest socialUpdateRequest,
                                     @RequestParam(value = "multipartFiles") List<MultipartFile> multipartFiles) {


        socialProfileService.updateProfileImage(id, multipartFiles);
        return "redirect:/social/profile/{id}";
    }

}
