package com.ll.hype.domain.order.orderrequest.service;

import com.ll.hype.domain.order.order.repository.OrderRepository;
import com.ll.hype.domain.order.orderrequest.dto.OrderReqResponse;
import com.ll.hype.domain.order.orderrequest.entity.OrderRequest;
import com.ll.hype.domain.order.orderrequest.repository.OrderRequestRepository;
import com.ll.hype.domain.shoes.shoes.dto.ShoesResponse;
import com.ll.hype.domain.shoes.shoes.repository.ShoesRepository;
import com.ll.hype.domain.shoes.shoes.service.ShoesService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class OrderRequestService {
    private final ShoesService shoesService;
    private final OrderRepository orderRepository;
    private final OrderRequestRepository orderRequestRepository;
    private final ShoesRepository shoesRepository;

    public ShoesResponse findByShoesId(Long shoesId) {
        return shoesService.findById(shoesId);
    }

    public OrderReqResponse findById(long id) {
        OrderRequest orderRequest = orderRequestRepository.findById(id).get();
        return OrderReqResponse.of(orderRequest);
    }

    public List<OrderReqResponse> findByShoesId(long id) {
        List<OrderReqResponse> orderRequestList = new ArrayList<>();
        for (OrderRequest orderRequest : orderRequestRepository.findByShoesId(id)) {
            orderRequestList.add(OrderReqResponse.of(orderRequest));
        }
        return orderRequestList;
    }
}
