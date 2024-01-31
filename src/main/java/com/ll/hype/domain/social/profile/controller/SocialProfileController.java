package com.ll.hype.domain.social.profile.controller;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.social.profile.dto.SocialProfileDto;
import com.ll.hype.domain.social.profile.service.SocialProfileService;
import com.ll.hype.global.security.authentication.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/social/profile")
public class SocialProfileController {
    @Autowired
    private SocialProfileService socialProfileService;
    @GetMapping("/{id}")
    public String getProfilePage(@PathVariable Long id, Model model, Principal principal) {
        if (principal != null) {
            UserPrincipal userPrincipal = (UserPrincipal) ((Authentication) principal).getPrincipal();
            Member loggedInUser = userPrincipal.getMember();

            model.addAttribute("principal", loggedInUser);

            SocialProfileDto socialProfileDto = socialProfileService.findById(id);

            model.addAttribute("profileDto", socialProfileDto);
            return "/domain/social/social/socialprofile";
        }
        return "redirect:/member/login";
    }
}
