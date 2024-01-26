package com.ll.hype.domain.order.salesrequest.service;

import com.ll.hype.domain.member.member.dto.JoinRequest;
import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.member.member.entity.MemberRole;
import com.ll.hype.domain.member.mypage.dto.ModifyRequest;
import com.ll.hype.domain.order.orderrequest.dto.OrderReqResponse;
import com.ll.hype.domain.order.orderrequest.entity.OrderRequest;
import com.ll.hype.domain.order.salesrequest.dto.SalesReqRequest;
import com.ll.hype.domain.order.salesrequest.dto.SalesReqResponse;
import com.ll.hype.domain.order.salesrequest.entity.SalesRequest;
import com.ll.hype.domain.order.salesrequest.repository.SalesRequestRepository;
import com.ll.hype.domain.shoes.shoes.dto.ShoesRequest;
import com.ll.hype.domain.shoes.shoes.dto.ShoesResponse;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.domain.shoes.shoes.entity.ShoesSize;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SalesRequestService {
    private final SalesRequestRepository salesRequestRepository;

    public SalesReqResponse findById(long id) {
        SalesRequest salesRequest = salesRequestRepository.findById(id).get();
        return SalesReqResponse.of(salesRequest);
    }

    public void selectSize(Long id, ShoesSize shoesSize, SalesReqRequest salesReqRequest) {
        SalesRequest findOne = salesRequestRepository.findById(id).get();

        SalesRequest salesRequest = SalesReqRequest.toEntity(salesReqRequest);
    }

    @Transactional
    public SalesReqResponse saveSalesRequest(SalesReqRequest salesReqRequest) {
        SalesRequest salesRequest = SalesReqRequest.toEntity(salesReqRequest);
        salesRequestRepository.save(salesRequest);
        return SalesReqResponse.of(salesRequest);
    }
}
