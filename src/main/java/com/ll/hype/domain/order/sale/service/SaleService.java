package com.ll.hype.domain.order.sale.service;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.order.buy.entity.Buy;
import com.ll.hype.domain.order.buy.repository.BuyRepository;
import com.ll.hype.domain.order.sale.dto.request.CreateSaleRequest;
import com.ll.hype.domain.order.sale.dto.response.SaleFormResponse;
import com.ll.hype.domain.order.sale.dto.response.SaleResponse;
import com.ll.hype.domain.order.sale.dto.response.SaleSizeInfoResponse;
import com.ll.hype.domain.order.sale.entity.Sale;
import com.ll.hype.domain.order.sale.repository.SaleRepository;
import com.ll.hype.domain.shoes.shoes.dto.ShoesResponse;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.domain.shoes.shoes.entity.ShoesSize;
import com.ll.hype.domain.shoes.shoes.repository.ShoesRepository;
import com.ll.hype.domain.shoes.shoes.repository.ShoesSizeRepository;
import com.ll.hype.domain.shoes.shoes.service.ShoesService;
import com.ll.hype.global.enums.Status;
import com.ll.hype.global.s3.image.ImageType;
import com.ll.hype.global.s3.image.imagebridge.component.ImageBridgeComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class SaleService {
    private final ShoesService shoesService;
    private final SaleRepository saleRepository;
    private final BuyRepository buyRepository;
    private final ShoesRepository shoesRepository;
    private final ShoesSizeRepository shoesSizeRepository;
    private final ImageBridgeComponent imageBridgeComponent;
    public ShoesResponse findByShoesId(Long shoesId) {
        return shoesService.findById(shoesId);
    }

    // 판매 입찰 조회
    public SaleResponse findById(long saleId) {
        Sale sale = saleRepository.findById(saleId)
            .orElseThrow(() -> new IllegalArgumentException("조회된 판매 입찰이 없습니다."));

        return SaleResponse.of(sale);
    }

    // 사이즈별 최고가
    public SaleSizeInfoResponse findByShoesSizeMaxPrice(Long id) {
        Shoes shoes = shoesRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("조회된 신발이 없습니다."));
        List<Buy> findByMaxPrice = buyRepository.findHighestPriceByShoesId(shoes);
        List<String> fullPath = imageBridgeComponent.findOneFullPath(ImageType.SHOES, shoes.getId());
        Map<Integer, Long> sizeMap = new LinkedHashMap<>();

        for (ShoesSize shoesSize : shoes.getSizes()) {
            sizeMap.put(shoesSize.getSize(), 0L);
        }

        for (Buy buy : findByMaxPrice) {
            sizeMap.put(buy.getShoesSize().getSize(), buy.getPrice());
        }
        return SaleSizeInfoResponse.of(shoes, sizeMap, fullPath);
    }

    public SaleFormResponse findByShoesSizeMaxPriceOne(Long id, int size) {
        Shoes shoes = shoesRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("조회된 신발이 없습니다."));
        Buy findByMaxPrice = buyRepository.findHighestPriceBuy(shoes, size);
        List<String> fullPath = imageBridgeComponent.findOneFullPath(ImageType.SHOES,
                findByMaxPrice.getShoes().getId());

        return SaleFormResponse.of(findByMaxPrice, fullPath);
    }

    // 판매 입찰 생성
    public SaleResponse createSaleBid(CreateSaleRequest saleRequest, Member member) {
        Shoes shoes = shoesRepository.findById(saleRequest.getShoesId())
                .orElseThrow(() -> new IllegalArgumentException("조회된 신발이 없습니다."));

        ShoesSize shoesSize = shoesSizeRepository.findByShoesAndSize(shoes, saleRequest.getSize())
                .orElseThrow(() -> new IllegalArgumentException("조회된 사이즈 정보가 없습니다."));

        Sale sale = Sale.builder()
                .shoes(shoes)
                .shoesSize(shoesSize)
                .member(member)
                .price(saleRequest.getPrice())
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(saleRequest.getEndDate()))
                .status(Status.BIDDING)
                .build();

        saleRepository.save(sale);

        return SaleResponse.of(sale);
    }

    //즉시 판매 생성
    public SaleResponse createSaleNow(CreateSaleRequest saleRequest, Member member) {
        Shoes shoes = shoesRepository.findById(saleRequest.getShoesId())
                .orElseThrow(() -> new IllegalArgumentException("조회된 신발이 없습니다."));

        ShoesSize shoesSize = shoesSizeRepository.findByShoesAndSize(shoes, saleRequest.getSize())
                .orElseThrow(() -> new IllegalArgumentException("조회된 사이즈 정보가 없습니다."));

        Sale sale = Sale.builder()
                .shoes(shoes)
                .shoesSize(shoesSize)
                .member(member)
                .price(saleRequest.getPrice())
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(saleRequest.getEndDate()))
                .status(Status.BID_COMPLETE)
                .build();

        saleRepository.save(sale);

        return SaleResponse.of(sale);
    }
}
