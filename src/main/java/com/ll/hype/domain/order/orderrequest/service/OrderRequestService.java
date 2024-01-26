package com.ll.hype.domain.order.orderrequest.service;

import com.ll.hype.domain.order.order.repository.OrderRepository;
import com.ll.hype.domain.shoes.shoes.dto.ShoesResponse;
import com.ll.hype.domain.shoes.shoes.service.ShoesService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@RequiredArgsConstructor
@Service
public class OrderRequestService {

    private final ShoesService shoesService;
    private final OrderRepository orderRepository;

    public ShoesResponse findByShoesId(Long shoesId) {
        return shoesService.findById(shoesId);
    }

}
