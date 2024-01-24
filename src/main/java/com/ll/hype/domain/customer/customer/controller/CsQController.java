package com.ll.hype.domain.customer.customer.controller;

import com.ll.hype.domain.customer.customer.dto.CustomerQRequest;
import com.ll.hype.domain.customer.customer.dto.CustomerQResponse;
import com.ll.hype.domain.customer.customer.service.CsQService;
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

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/cs")
@Controller
public class CsQController {
    private final CsQService csQService;

    @GetMapping("/question/create")
    public String questionSaveForm(CustomerQRequest customerQRequest) {
        return "";
    }

    @PostMapping("/question/create")
    public String questionSave(CustomerQRequest customerQRequest) {
        csQService.questionSave(customerQRequest);
        return "";
    }

    @GetMapping("/question/update/{id}")
    public String questionUpdateForm(@PathVariable("id") Long id, Model model) {
        CustomerQResponse findOne = csQService.findOne(id);
        model.addAttribute("question", findOne);
        return "";
    }

    @PostMapping("/question/update")
    public String questionUpdate(@RequestParam("id") Long id,
                                 CustomerQRequest customerQRequest) {
        csQService.questionUpdate(id, customerQRequest);
        return "";
    }

    @PostMapping("/question/delete/{id}")
    public String questionDelete(@PathVariable("id") Long id) {
        csQService.questionDelete(id);
        return "";
    }

    @GetMapping("/question/detail/{id}")
    public String questionDetatil(@PathVariable("id") Long id, Model model) {
        CustomerQResponse findOne = csQService.findOne(id);
        model.addAttribute("question", findOne);
        return "";
    }

    @GetMapping("/question/list")
    public String questionFindAll(Model model) {
        List<CustomerQResponse> findAll = csQService.findAll();
        model.addAttribute("questions", findAll);
        return "";
    }
}
