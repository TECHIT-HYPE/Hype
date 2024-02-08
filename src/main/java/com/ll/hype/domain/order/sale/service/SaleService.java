package com.ll.hype.domain.order.sale.service;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.order.buy.entity.Buy;
import com.ll.hype.domain.order.buy.repository.BuyRepository;
import com.ll.hype.domain.order.order.dto.response.OrderResponse;
import com.ll.hype.domain.order.order.entity.OrderStatus;
import com.ll.hype.domain.order.order.entity.Orders;
import com.ll.hype.domain.order.order.entity.PaymentStatus;
import com.ll.hype.domain.order.order.repository.OrderRepository;
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
    private final OrderRepository orderRepository;
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
        Optional<Buy> highestPriceBuy = buyRepository.findHighestPriceBuy(shoes, size);

        if (highestPriceBuy.isPresent()) {
            price = highestPriceBuy.get().getPrice();
        }

        List<String> fullPath = imageBridgeComponent.findOneFullPath(ImageType.SHOES, shoes.getId());

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
    public OrderResponse createSaleNow(CreateSaleRequest saleRequest, Member member) {
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

        Buy buy = buyRepository.findHighestPriceBuy(shoes, saleRequest.getSize())
                .orElseThrow(() -> new IllegalArgumentException("구매입찰 내역을 찾을 수 없습니다."));

        buy.updateStatus(Status.BID_COMPLETE);

        if (!buy.getPrice().equals(sale.getPrice())) {
            throw new IllegalArgumentException("거래 성사 금액이 일치하지 않습니다.");
        }

        Orders order = Orders.builder()
                .buy(buy)
                .sale(sale)
                .orderDate(LocalDate.now())
                .orderPrice(buy.getPrice())
                .receiverName(buy.getReceiverName())
                .receiverPhoneNumber(buy.getReceiverPhoneNumber())
                .receiverAddress(buy.getReceiverAddress())
                .status(OrderStatus.TRADING)
                .paymentStatus(PaymentStatus.WAIT_PAYMENT)
                .build();
        orderRepository.save(order);

        return OrderResponse.of(order, fullPath);
    }

    public boolean confirmSale(Member member, Long id, int size) {
        Optional<Sale> findOne = saleRepository.findByShoesIdAndMemberAndShoesSizeSize(id, member, size);

        if (findOne.isEmpty()) {return true;}
        return false;
    }

}
