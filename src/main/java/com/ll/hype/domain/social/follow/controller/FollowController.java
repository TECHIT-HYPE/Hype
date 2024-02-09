package com.ll.hype.domain.social.follow.controller;

import com.ll.hype.domain.social.follow.service.FollowService;
import com.ll.hype.global.security.authentication.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class FollowController {
    @Autowired
    FollowService followService;

    @PostMapping("/social/follow/{toMemberId}")
    public String follow(@PathVariable Long toMemberId, @AuthenticationPrincipal UserPrincipal userPrincipal, Model model) {
        followService.saveFollow(toMemberId, userPrincipal.getMember().getId());
        return "redirect:/social/profile/{toMemberId}";
    }

    @PostMapping("/social/unfollow/{toMemberId}")
    public String unfollow(@PathVariable Long toMemberId, @AuthenticationPrincipal UserPrincipal userPrincipal, Model model) {
        followService.deleteFollow(toMemberId, userPrincipal.getMember().getId());
        return "redirect:/social/profile/{toMemberId}";
    }


}
