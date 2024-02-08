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
import java.util.Optional;

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
        Shoes shoes = shoesRepository.findById(shoesId)
                .orElseThrow(() -> new IllegalArgumentException("조회된 객체가 없습니다."));
        List<String> fullPath = imageBridgeComponent.findOneFullPath(ImageType.SHOES, shoes.getId());

        return ShoesResponse.of(shoes, fullPath);
    }

    // 판매 입찰 조회
    public SaleResponse findById(long saleId) {
        Sale sale = saleRepository.findById(saleId)
            .orElseThrow(() -> new IllegalArgumentException("조회된 판매 입찰이 없습니다."));
        List<String> fullPath = imageBridgeComponent.findOneFullPath(ImageType.SHOES, sale.getShoes().getId());

        return SaleResponse.of(sale, fullPath);
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
        long price = 0L;
        Shoes shoes = shoesRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("조회된 신발이 없습니다."));
        Optional<Buy> findByMaxPrice = buyRepository.findHighestPriceBuy(shoes, size);

        if (findByMaxPrice.isPresent()) {
            price = findByMaxPrice.get().getPrice();
        }

        List<String> fullPath = imageBridgeComponent.findOneFullPath(ImageType.SHOES,
                shoes.getId());

        return SaleFormResponse.of(shoes, size, price, fullPath);
    }

    // 판매 입찰 생성
    public SaleResponse createSaleBid(CreateSaleRequest saleRequest, Member member) {
        Shoes shoes = shoesRepository.findById(saleRequest.getShoesId())
                .orElseThrow(() -> new IllegalArgumentException("조회된 신발이 없습니다."));

        ShoesSize shoesSize = shoesSizeRepository.findByShoesAndSize(shoes, saleRequest.getSize())
                .orElseThrow(() -> new IllegalArgumentException("조회된 사이즈 정보가 없습니다."));

        List<String> fullPath = imageBridgeComponent.findOneFullPath(ImageType.SHOES, shoes.getId());

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

        return SaleResponse.of(sale, fullPath);
    }

    //즉시 판매 생성
    public SaleResponse createSaleNow(CreateSaleRequest saleRequest, Member member) {
        Shoes shoes = shoesRepository.findById(saleRequest.getShoesId())
                .orElseThrow(() -> new IllegalArgumentException("조회된 신발이 없습니다."));

        ShoesSize shoesSize = shoesSizeRepository.findByShoesAndSize(shoes, saleRequest.getSize())
                .orElseThrow(() -> new IllegalArgumentException("조회된 사이즈 정보가 없습니다."));

        List<String> fullPath = imageBridgeComponent.findOneFullPath(ImageType.SHOES, shoes.getId());

        Sale sale = Sale.builder()
                .shoes(shoes)
                .shoesSize(shoesSize)
                .member(member)
                .price(saleRequest.getPrice())
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .address(saleRequest.getAddress())
                .status(Status.BID_COMPLETE)
                .account(saleRequest.getAccount())
                .build();

        saleRepository.save(sale);

        return SaleResponse.of(sale, fullPath);
    }
}
