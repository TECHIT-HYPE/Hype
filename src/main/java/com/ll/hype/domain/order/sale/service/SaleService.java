package com.ll.hype.domain.order.sale.service;

import com.ll.hype.domain.order.sale.dto.SaleRequest;
import com.ll.hype.domain.order.sale.dto.SaleResponse;
import com.ll.hype.domain.order.sale.entity.Sale;
import com.ll.hype.domain.order.sale.repository.SaleRepository;
import com.ll.hype.domain.shoes.shoes.entity.ShoesSize;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SaleService {
    private final SaleRepository saleRepository;

    public SaleResponse findById(long id) {
        Sale salesRequest = saleRepository.findById(id).get();
        return SaleResponse.of(salesRequest);
    }

    public void selectSize(Long id, ShoesSize shoesSize, SaleRequest salesReqRequest) {
        Sale findOne = saleRepository.findById(id).get();

        Sale salesRequest = SaleRequest.toEntity(salesReqRequest);
    }

    @Transactional
    public SaleResponse saveSalesRequest(SaleRequest salesReqRequest) {
        Sale salesRequest = SaleRequest.toEntity(salesReqRequest);
        saleRepository.save(salesRequest);
        return SaleResponse.of(salesRequest);
    }
}
