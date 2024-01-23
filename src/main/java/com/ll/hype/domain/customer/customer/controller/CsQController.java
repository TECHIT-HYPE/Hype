package com.ll.hype.domain.customer.customer.controller;

import com.ll.hype.domain.customer.customer.dto.CustomerQRequest;
import com.ll.hype.domain.customer.customer.service.CsQService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/csq")
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
}
