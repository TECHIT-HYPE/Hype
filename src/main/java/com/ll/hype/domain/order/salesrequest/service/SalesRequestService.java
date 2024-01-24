package com.ll.hype.domain.order.salesrequest.service;

import com.ll.hype.domain.member.member.dto.JoinRequest;
import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.member.member.entity.MemberRole;
import com.ll.hype.domain.member.mypage.dto.ModifyRequest;
import com.ll.hype.domain.order.salesrequest.repository.SalesRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SalesRequestService {
    private final SalesRequestRepository salesRequestRepository;

}
