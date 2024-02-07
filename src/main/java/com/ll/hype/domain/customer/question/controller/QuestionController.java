package com.ll.hype.domain.customer.question.controller;

import com.ll.hype.domain.customer.question.dto.QuestionRequest;
import com.ll.hype.domain.customer.question.dto.QuestionResponse;
import com.ll.hype.domain.customer.question.service.QuestionService;
import com.ll.hype.global.security.authentication.UserPrincipal;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/cs")
@Controller
public class QuestionController {
    private final QuestionService questionService;

    @GetMapping("/main")
    public String questionMain() {
        return "domain/cs/main";
    }

    @GetMapping("/question/create")
    public String questionSaveForm(QuestionRequest questionRequest) {
        return "domain/cs/question/add_form";
    }

    @PostMapping("/question/create")
    public String questionSave(@AuthenticationPrincipal UserPrincipal user,
                               @RequestParam(value = "files") List<MultipartFile> files,
                               QuestionRequest questionRequest) {
        questionService.questionSave(questionRequest, user.getMember(), files);
        return "redirect:/cs/main";
    }

    @GetMapping("/question/list")
    public String questionList(@AuthenticationPrincipal UserPrincipal user,
                               Model model) {
        List<QuestionResponse> findByQuestions = questionService.findByMyList(user.getMember());
        model.addAttribute("questions", findByQuestions);
        return "domain/cs/question/list";
    }

    @GetMapping("/question/update/{id}")
    public String questionUpdateForm(@PathVariable("id") Long id,
                                     @AuthenticationPrincipal UserPrincipal user,
                                     QuestionRequest customerQRequest,
                                     Model model) {
        QuestionResponse findQuestion = questionService.findOne(id, user.getMember());
        model.addAttribute("question", findQuestion);
        return "domain/cs/question/update_form";
    }

    @PutMapping("/question/update")
    public String questionUpdate(@RequestParam("id") Long id,
                                 @AuthenticationPrincipal UserPrincipal user,
                                 QuestionRequest customerQRequest) {
        questionService.questionUpdate(id, customerQRequest, user.getMember());
        return "redirect:/cs/question/detail/%s".formatted(id);
    }

    @DeleteMapping("/question/delete/{id}")
    public String questionDelete(@PathVariable("id") Long id,
                                 @AuthenticationPrincipal UserPrincipal user) {
        questionService.questionDelete(id, user.getMember());
        return "redirect:/cs/question/list";
    }

    @GetMapping("/question/detail/{id}")
    public String questionDetail(@PathVariable("id") Long id,
                                 @AuthenticationPrincipal UserPrincipal user,
                                 Model model) {
        QuestionResponse findOne = questionService.findOne(id, user.getMember());
        model.addAttribute("question", findOne);
        return "domain/cs/question/detail";
    }
}
