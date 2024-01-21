package com.ll.hype.domain.member.member.controller;

import com.ll.hype.domain.member.member.dto.JoinRequest;
import com.ll.hype.domain.member.member.dto.LoginRequest;
import com.ll.hype.domain.member.member.service.MemberService;
import com.ll.hype.global.enums.Gender;
import com.ll.hype.global.util.ShoesSizeGenerator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String joinForm(JoinRequest joinRequest, Model model) {
        return loadAndReturnJoinForm(model);
    }

    @PostMapping("/join")
    public String join(@Valid JoinRequest joinRequest, BindingResult bindingResult, Model model) {

        if (bindingResult.hasErrors()) {
            return loadAndReturnJoinForm(model);
        }

        if (!memberService.confirmPassword(joinRequest.getPassword(), joinRequest.getPasswordConfirm())) {
            bindingResult.reject("passwordIncorrect", "비밀번호가 일치하지 않습니다.");
            return loadAndReturnJoinForm(model);
        }

        if (memberService.existsByEmail(joinRequest.getEmail())) {
            bindingResult.reject("existEmail", "이미 존재하는 이메일입니다.");
            return loadAndReturnJoinForm(model);
        }

        if (memberService.existsByNickname(joinRequest.getNickname())) {
            bindingResult.reject("existNickname", "이미 존재하는 별명입니다.");
            return loadAndReturnJoinForm(model);
        }

        if (memberService.existsByPhoneNumber(joinRequest.getPhoneNumber())) {
            bindingResult.reject("existPhoneNumber", "이미 존재하는 전화번호입니다.");
            return loadAndReturnJoinForm(model);
        }

        memberService.join(joinRequest);

        return "redirect:/member/login";
    }

    private String loadAndReturnJoinForm(Model model) {
        model.addAttribute("genderList", Gender.values());
        model.addAttribute("shoesSizeList", ShoesSizeGenerator.getSizes());
        return "domain/member/member/join";
    }

    @GetMapping("/login")
    public String loginForm(LoginRequest loginRequest) {
        return "domain/member/member/login";
    }
}
