package com.ll.hype.domain.shoes.shoes.service;

import com.ll.hype.domain.order.buy.repository.BuyRepository;
import com.ll.hype.domain.order.order.dto.response.OrderPriceResponse;
import com.ll.hype.domain.order.order.entity.Orders;
import com.ll.hype.domain.order.order.repository.OrderRepository;
import com.ll.hype.domain.shoes.shoes.dto.ShoesResponse;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.domain.shoes.shoes.entity.ShoesCategory;
import com.ll.hype.domain.shoes.shoes.repository.ShoesRepository;
import com.ll.hype.global.enums.StatusCode;
import com.ll.hype.global.exception.custom.EntityNotFoundException;
import com.ll.hype.global.s3.image.ImageType;
import com.ll.hype.global.s3.image.imagebridge.component.ImageBridgeComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ShoesService {
    private final ShoesRepository shoesRepository;
    private final BuyRepository buyRepository;
    private final OrderRepository orderRepository;
    private final ImageBridgeComponent imageBridgeComponent;
    public ShoesResponse findById(long id) {
        Shoes shoes = shoesRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Shoes Not Found"));
        List<String> fullPath = imageBridgeComponent.findAllFullPath(ImageType.SHOES, shoes.getId());
        return ShoesResponse.of(shoes, fullPath);
    }

    public List<ShoesResponse> getShoesList(Optional<List<String>> optionalCategories) {
        List<ShoesResponse> shoesList = new ArrayList<>();

        // 카테고리 선택 리스트
            // 사용자가 하나 이상의 카테고리를 선택할 경우
        if (optionalCategories.isPresent() && !optionalCategories.get().isEmpty()) {
            // 문자열 리스트를 열거형으로 변환
            List<ShoesCategory> categories = optionalCategories.get().stream()
                    .map(ShoesCategory::valueOf)
                    .collect(Collectors.toList());
            for (Shoes shoes : shoesRepository.findByCategories(categories)) {
                List<String> fullPath = imageBridgeComponent.findOneFullPath(ImageType.SHOES, shoes.getId());
                shoesList.add(ShoesResponse.of(shoes, fullPath));
            }
        }
        // 기본 리스트
        else {
            for (Shoes shoes : shoesRepository.findByStatus(StatusCode.ENABLE)) {
                List<String> fullPath = imageBridgeComponent.findOneFullPath(ImageType.SHOES, shoes.getId());
                shoesList.add(ShoesResponse.of(shoes, fullPath));
            }
        }
        return shoesList;
    }

    public List<ShoesResponse> findAll() {
        List<ShoesResponse> shoesList = new ArrayList<>();
        for (Shoes shoes : shoesRepository.findByStatus(StatusCode.ENABLE)) {
            List<String> fullPath = imageBridgeComponent.findOneFullPath(ImageType.SHOES, shoes.getId());
            shoesList.add(ShoesResponse.of(shoes, fullPath));
        }
        return shoesList;
    }

    // 최근 거래가
    @Transactional
    public Optional<OrderPriceResponse> getLatestTradePrice(long id) {
        return orderRepository.findByShoesId(id)
                .map(OrderPriceResponse::of);
    }
}
