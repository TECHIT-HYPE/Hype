package com.ll.hype.domain.order.buy.service;

import com.ll.hype.domain.order.buy.dto.BuyResponse;
import com.ll.hype.domain.order.order.repository.OrderRepository;
import com.ll.hype.domain.order.buy.entity.Buy;
import com.ll.hype.domain.order.buy.repository.BuyRepository;
import com.ll.hype.domain.shoes.shoes.dto.ShoesResponse;
import com.ll.hype.domain.shoes.shoes.repository.ShoesRepository;
import com.ll.hype.domain.shoes.shoes.service.ShoesService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    //신발 사이즈 별로 그룹화, 그룹 내 높은가격순, 입찰순으로 정렬
    public Map<Integer, Optional<BuyResponse>> getHighestBidsGroupedBySize(long id) {
        List<BuyResponse> bids = findByShoes(id);
        return bids.stream()
                .collect(Collectors.groupingBy(
                        buyResponse -> buyResponse.getShoesSize().getSize(),
                        Collectors.maxBy(Comparator.comparing(BuyResponse::getPrice)
                                .thenComparing(BuyResponse::getCreateDate))
                ));
    }

//    public List<BuyResponse> findByShoesIdAndShoesSize(long id, int shoesSize) {
//        List<BuyResponse> sizeList = new ArrayList<>();
//        for (Buy buy : buyRepository.findByShoesIdAndShoesSize_Size(id, shoesSize)) {
//            sizeList.add(BuyResponse.of(buy));
//        }
//        return sizeList;
//    }
//
//    public BuyResponse findHighestBidByShoesAndSize(Long id, int shoesSize) {
//        Buy buy = buyRepository.findHighestBidByShoesAndSize(id, shoesSize);
//        return BuyResponse.of(buy);
//    }

}
