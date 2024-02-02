package com.ll.hype.domain.order.sale.service;

import com.ll.hype.domain.order.buy.repository.BuyRepository;
import com.ll.hype.domain.order.sale.dto.SaleRequest;
import com.ll.hype.domain.order.sale.dto.SaleResponse;
import com.ll.hype.domain.order.sale.entity.Sale;
import com.ll.hype.domain.order.sale.repository.SaleRepository;
import com.ll.hype.domain.shoes.shoes.dto.ShoesResponse;
import com.ll.hype.domain.shoes.shoes.entity.ShoesSize;
import com.ll.hype.domain.shoes.shoes.service.ShoesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class SaleService {
    private final ShoesService shoesService;
    private final SaleRepository saleRepository;
    private final BuyRepository buyRepository;

    public ShoesResponse findByShoesId(Long shoesId) {
        return shoesService.findById(shoesId);
    }

    public SaleResponse findById(long id) {
        Sale salesRequest = saleRepository.findById(id).get();
        return SaleResponse.of(salesRequest);
    }

    public void selectSize(Long id, ShoesSize shoesSize, SaleRequest salesReqRequest) {
        Sale findOne = saleRepository.findById(id).get();

        Sale salesRequest = SaleRequest.toEntity(salesReqRequest);
    }

    @Transactional
    public void saveSaleRequest(SaleRequest saleRequest) {
        Sale sale = SaleRequest.toEntity(saleRequest);
        saleRepository.save(sale);
    }
}
