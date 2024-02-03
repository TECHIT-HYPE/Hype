package com.ll.hype.domain.social.social.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/social/feed")
public class socialController {


    @GetMapping("")
    public String getFeed() {
        return "/domain/social/social/social/socialupload";
    }
}
