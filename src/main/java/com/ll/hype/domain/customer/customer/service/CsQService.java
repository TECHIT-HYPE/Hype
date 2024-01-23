package com.ll.hype.domain.customer.customer.service;

import com.ll.hype.domain.customer.customer.dto.CustomerQRequest;
import com.ll.hype.domain.customer.customer.entity.CustomerQ;
import com.ll.hype.domain.customer.customer.repository.CsQRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CsQService {
    private final CsQRepository csQRepository;

    public CustomerQ questionSave(CustomerQRequest customerQRequest) {
        CustomerQ customerQ = CustomerQRequest.toEntity(customerQRequest);
        csQRepository.save(customerQ);
        return customerQ;
    }
}
