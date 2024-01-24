package com.ll.hype.domain.order.salesrequest.controller;

import com.ll.hype.domain.order.salesrequest.service.SalesRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/sale")
@Controller
public class SalesRequestController {

    private final SalesRequestService salesRequestService;



}
