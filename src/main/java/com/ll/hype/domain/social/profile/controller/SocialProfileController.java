package com.ll.hype.domain.social.profile.controller;

import com.ll.hype.domain.social.profile.dto.SocialProfileDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/social/profile")
public class SocialProfileController {
    @GetMapping("/{id}")
    public String profile(@PathVariable String nickname, Model model) {
        SocialProfileDto socialProfileDto =
    }
}
