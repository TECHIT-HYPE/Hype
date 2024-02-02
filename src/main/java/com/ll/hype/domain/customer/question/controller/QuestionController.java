package com.ll.hype.domain.customer.question.controller;

import com.ll.hype.domain.customer.question.dto.QuestionRequest;
import com.ll.hype.domain.customer.question.dto.QuestionResponse;
import com.ll.hype.domain.customer.question.service.QuestionService;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/cs")
@Controller
public class QuestionController {
    private final QuestionService csQService;

    @GetMapping("/main")
    public String questionMain() {
        return "domain/cs/main";
    }

    @GetMapping("/question/create")
    public String questionSaveForm(QuestionRequest questionRequest) {
        return "domain/cs/question/addForm";
    }

    @PostMapping("/question/create")
    public String questionSave(QuestionRequest questionRequest,
                               Principal principal,
                               @RequestParam(value = "files") List<MultipartFile> files) {
        String email = principal.getName();
        csQService.questionSave(questionRequest, email, files);
        return "redirect:/cs/main";
    }

    @GetMapping("/question/list")
    public String questionList(Model model, Principal principal) {
        String email = principal.getName();
        List<QuestionResponse> findByQuestions = csQService.findByMyList(email);
        model.addAttribute("questions", findByQuestions);
        return "domain/cs/question/list";
    }
    @GetMapping("/question/update/{id}")
    public String questionUpdateForm(@PathVariable("id") Long id,
                                     Model model,
                                     Principal principal,
                                     QuestionRequest customerQRequest) {
        String email = principal.getName();
        QuestionResponse findQuestion = csQService.findOne(id, email);
        model.addAttribute("question", findQuestion);
        return "domain/cs/question/updateForm";
    }

    @PostMapping("/question/update")
    public String questionUpdate(@RequestParam("id") Long id,
                                 QuestionRequest customerQRequest,
                                 Principal principal) {
        String email = principal.getName();
        csQService.questionUpdate(id, customerQRequest,email);
        return "redirect:/cs/question/detail/%s".formatted(id);
    }

    @PostMapping("/question/delete/{id}")
    public String questionDelete(@PathVariable("id") Long id, Principal principal) {
        String email = principal.getName();
        csQService.questionDelete(id, email);
        return "redirect:/cs/question/list";
    }

    @GetMapping("/question/detail/{id}")
    public String questionDetail(@PathVariable("id") Long id, Model model, Principal principal) {
        String email = principal.getName();
        QuestionResponse findOne = csQService.findOne(id, email);
        model.addAttribute("question", findOne);
        return "domain/cs/question/detail";
    }
}
