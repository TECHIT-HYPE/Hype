package com.ll.hype.domain.social.likes.controller;

import com.ll.hype.domain.social.likes.service.LikesService;
import com.ll.hype.global.security.authentication.UserPrincipal;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/social/feed/{id}")
public class LikesController {

    private final LikesService likesService;

    @PostMapping("/likes")
    public String savelikes(@PathVariable("id") Long id, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        likesService.saveLikes(id, userPrincipal.getMember().getId());
        return "redirect:/social/feed/%s".formatted(id);
    }

    @PostMapping("/unlikes")
    public String deletelikes(@PathVariable("id") Long id, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        likesService.deleteLikes(id, userPrincipal.getMember().getId());
        return "redirect:/social/feed/%s".formatted(id);
    }
}
