package com.ll.hype.domain.customer.customer.service;

import com.ll.hype.domain.customer.customer.dto.CustomerQRequest;
import com.ll.hype.domain.customer.customer.dto.CustomerQResponse;
import com.ll.hype.domain.customer.customer.entity.CustomerQ;
import com.ll.hype.domain.customer.customer.repository.CsQRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CsQService {
    private final CsQRepository csQRepository;

    @Transactional
    public CustomerQ questionSave(CustomerQRequest customerQRequest) {
        CustomerQ customerQ = CustomerQRequest.toEntity(customerQRequest);
        csQRepository.save(customerQ);
        return customerQ;
    }

    @Transactional
    public void questionUpdate(Long id, CustomerQRequest customerQRequest) {
        CustomerQ findOne = csQRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("조회된 문의사항이 없습니다."));

        CustomerQ customerQ = CustomerQRequest.toEntity(customerQRequest);
        findOne.update(customerQ);
    }

    @Transactional
    public void questionDelete(Long id) {
        CustomerQ findOne = csQRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("조회된 문의사항이 없습니다."));

        csQRepository.delete(findOne);
    }
    public CustomerQResponse findOne(Long id) {
        CustomerQ findOne = csQRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("조회된 문의사항이 없습니다."));
        return CustomerQResponse.of(findOne);
    }

    public List<CustomerQResponse> findAll() {
        List<CustomerQResponse> questions = new ArrayList<>();

        for (CustomerQ customerQ : csQRepository.findAll()) {
            questions.add(CustomerQResponse.of(customerQ));
        }

        return questions;
    }
}
