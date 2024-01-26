package com.ll.hype.domain.order.orderrequest.service;

import com.ll.hype.domain.order.orderrequest.dto.OrderReqResponse;
import com.ll.hype.domain.order.orderrequest.entity.OrderRequest;
import com.ll.hype.domain.order.orderrequest.repository.OrderRequestRepository;
import com.ll.hype.domain.shoes.shoes.dto.ShoesResponse;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.domain.shoes.shoes.repository.ShoesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class OrderRequestService {
    private final OrderRequestRepository orderRequestRepository;
    private final ShoesRepository shoesRepository;
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
