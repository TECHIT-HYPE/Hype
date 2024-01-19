package com.ll.hype.domain.member.member.controller;

import com.ll.hype.domain.member.member.dto.JoinRequest;
import com.ll.hype.domain.member.member.service.MemberService;
import com.ll.hype.global.enums.Gender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/join")
    public String joinForm(Model model, JoinRequest joinRequest) {
        model.addAttribute("genders", Gender.values());
        return "domain/member/member/join";
    }

    @PostMapping("/join")
    public String join(JoinRequest joinRequest) {
        memberService.join(joinRequest);
        return "/home";
    }
}
