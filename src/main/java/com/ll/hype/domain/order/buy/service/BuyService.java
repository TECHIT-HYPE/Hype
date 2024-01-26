package com.ll.hype.domain.order.buy.service;

import com.ll.hype.domain.order.buy.dto.BuyResponse;
import com.ll.hype.domain.order.order.repository.OrderRepository;
import com.ll.hype.domain.order.buy.entity.Buy;
import com.ll.hype.domain.order.buy.repository.BuyRepository;
import com.ll.hype.domain.shoes.shoes.dto.ShoesResponse;
import com.ll.hype.domain.shoes.shoes.repository.ShoesRepository;
import com.ll.hype.domain.shoes.shoes.service.ShoesService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BuyService {
    private final ShoesService shoesService;
    private final OrderRepository orderRepository;
    private final BuyRepository buyRepository;
    private final ShoesRepository shoesRepository;

    public ShoesResponse findByShoesId(Long shoesId) {
        return shoesService.findById(shoesId);
    }

    public BuyResponse findById(long id) {
        Buy orderRequest = buyRepository.findById(id).get();
        return BuyResponse.of(orderRequest);
    }

    public List<BuyResponse> findByShoes(long id) {
        List<BuyResponse> orderRequestList = new ArrayList<>();
        for (Buy orderRequest : buyRepository.findByShoesId(id)) {
            orderRequestList.add(BuyResponse.of(orderRequest));
        }
        return orderRequestList;
    }
}
