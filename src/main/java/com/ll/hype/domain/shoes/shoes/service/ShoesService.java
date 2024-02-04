package com.ll.hype.domain.shoes.shoes.service;

import com.ll.hype.domain.order.buy.entity.Buy;
import com.ll.hype.domain.order.buy.repository.BuyRepository;
import com.ll.hype.domain.order.sale.dto.SaleSizeInfoResponse;
import com.ll.hype.domain.shoes.shoes.dto.ShoesResponse;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.domain.shoes.shoes.entity.ShoesSize;
import com.ll.hype.domain.shoes.shoes.repository.ShoesRepository;
import com.ll.hype.global.enums.StatusCode;
import com.ll.hype.global.s3.image.ImageType;
import com.ll.hype.global.s3.image.imagebridge.component.ImageBridgeComponent;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class ShoesService {
    private final ShoesRepository shoesRepository;
    private final BuyRepository buyRepository;
    private final ImageBridgeComponent imageBridgeComponent;
    public ShoesResponse findById(long id) {
        Shoes shoes = shoesRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("조회된 신발이 없습니다."));
        List<String> fullPath = imageBridgeComponent.findAllFullPath(ImageType.SHOES, shoes.getId());
        return ShoesResponse.of(shoes, fullPath);
    }

    public List<ShoesResponse> findAll() {
        List<ShoesResponse> shoesList = new ArrayList<>();
        for (Shoes shoes : shoesRepository.findByStatus(StatusCode.ENABLE)) {
            List<String> fullPath = imageBridgeComponent.findOneFullPath(ImageType.SHOES, shoes.getId());
            shoesList.add(ShoesResponse.of(shoes, fullPath));
        }
        return shoesList;
    }

}
