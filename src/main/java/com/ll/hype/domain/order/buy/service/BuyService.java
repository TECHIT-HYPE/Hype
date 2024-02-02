package com.ll.hype.domain.order.buy.service;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.order.buy.dto.CreateBuyRequest;
import com.ll.hype.domain.order.buy.dto.BuyResponse;
import com.ll.hype.domain.order.buy.dto.BuySizeInfoResponse;
import com.ll.hype.domain.order.buy.entity.Buy;
import com.ll.hype.domain.order.buy.repository.BuyRepository;
import com.ll.hype.domain.order.order.repository.OrderRepository;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class BuyService {
    private final BuyRepository buyRepository;
    private final SaleRepository saleRepository;
    private final OrderRepository orderRepository;

    private final ShoesRepository shoesRepository;
    private final ShoesSizeRepository shoesSizeRepository;
    private final ImageBridgeComponent imageBridgeComponent;


    public ShoesResponse findByShoesId(Long shoesId) {
        Shoes shoes = shoesRepository.findById(shoesId)
                .orElseThrow(() -> new IllegalArgumentException("조회된 객체가 없습니다."));

        List<String> fullPath = imageBridgeComponent.findOneFullPath(ImageType.SHOES, shoes.getId());
        return ShoesResponse.of(shoes, fullPath);
    }

    public BuyResponse findByBuyId(long id) {
        Buy orderRequest = buyRepository.findById(id).get();
        log.info("[BuyService.findById] id : " + orderRequest.getId());
        return BuyResponse.of(orderRequest);
    }

    public List<BuyResponse> findByShoes(long id) {
        List<BuyResponse> orderRequestList = new ArrayList<>();
        for (Buy orderRequest : buyRepository.findByShoesId(id)) {
            orderRequestList.add(BuyResponse.of(orderRequest));
        }
        return orderRequestList;
    }

    public BuySizeInfoResponse findByShoesSizeMinPrice(Long id) {
        Shoes shoes = shoesRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("조회된 신발이 없습니다."));
        List<Sale> findByMinPrice = saleRepository.findLowestPriceByShoesId(shoes);
        List<String> fullPath = imageBridgeComponent.findOneFullPath(ImageType.SHOES, shoes.getId());
        Map<Integer, Long> sizeMap = new LinkedHashMap<>();

        for (ShoesSize shoesSize : shoes.getSizes()) {
            sizeMap.put(shoesSize.getSize(), 0L);
        }

        // size 전체를 가져와서 그걸 링크드해시맵에 써야해
        for (Sale sale : findByMinPrice) {
            sizeMap.put(sale.getShoesSize().getSize(), sale.getPrice());
        }

        return BuySizeInfoResponse.of(shoes, sizeMap, fullPath);
    }

    public BuyResponse createBuy(CreateBuyRequest buyRequest, Member member) {
        Shoes shoes = shoesRepository.findById(buyRequest.getShoesId())
                .orElseThrow(() -> new IllegalArgumentException("조회된 신발이 없습니다."));

        ShoesSize shoesSize = shoesSizeRepository.findByShoesAndSize(shoes, buyRequest.getSize())
                .orElseThrow(() -> new IllegalArgumentException("조회된 사이즈 정보가 없습니다."));

        Buy buy = Buy.builder()
                .shoes(shoes)
                .shoesSize(shoesSize)
                .member(member)
                .price(buyRequest.getPrice())
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(buyRequest.getEndDate()))
                .status(Status.BIDDING)
                .build();

        buyRepository.save(buy);

        return BuyResponse.of(buy);
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

    public BuyResponse findById(long highestBidId) {
        Buy buy = buyRepository.findById(highestBidId)
                .orElseThrow(() -> new IllegalArgumentException("조회된 구매입찰 내역이 없습니다."));
        return BuyResponse.of(buy);
    }
}
