package com.ll.hype.domain.order.buy.service;

import com.ll.hype.domain.address.address.entity.Address;
import com.ll.hype.domain.address.address.repository.AddressRepository;
import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.order.buy.dto.response.BuyFormResponse;
import com.ll.hype.domain.order.buy.dto.request.CreateBuyRequest;
import com.ll.hype.domain.order.buy.dto.response.BuyResponse;
import com.ll.hype.domain.order.buy.dto.response.BuySizeInfoResponse;
import com.ll.hype.domain.order.buy.entity.Buy;
import com.ll.hype.domain.order.buy.repository.BuyRepository;
import com.ll.hype.domain.order.order.dto.response.OrderResponse;
import com.ll.hype.domain.order.order.entity.OrderStatus;
import com.ll.hype.domain.order.order.entity.Orders;
import com.ll.hype.domain.order.order.entity.PaymentStatus;
import com.ll.hype.domain.order.order.repository.OrderRepository;
import com.ll.hype.domain.order.sale.entity.Sale;
import com.ll.hype.domain.order.sale.repository.SaleRepository;
import com.ll.hype.domain.shoes.shoes.dto.ShoesResponse;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.domain.shoes.shoes.entity.ShoesSize;
import com.ll.hype.domain.shoes.shoes.repository.ShoesRepository;
import com.ll.hype.domain.shoes.shoes.repository.ShoesSizeRepository;
import com.ll.hype.global.enums.Status;
import com.ll.hype.global.s3.image.ImageType;
import com.ll.hype.global.s3.image.imagebridge.component.ImageBridgeComponent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
public class BuyService {
    private final BuyRepository buyRepository;
    private final SaleRepository saleRepository;

    private final ShoesRepository shoesRepository;
    private final ShoesSizeRepository shoesSizeRepository;
    private final ImageBridgeComponent imageBridgeComponent;
    private final OrderRepository orderRepository;
    private final AddressRepository addressRepository;

    /**
     * Shoes Id로 Shoes 조회
     *
     * @param shoesId
     * @return
     */
    public ShoesResponse findByShoesId(Long shoesId) {
        Shoes shoes = shoesRepository.findById(shoesId)
                .orElseThrow(() -> new IllegalArgumentException("조회된 객체가 없습니다."));

        List<String> fullPath = imageBridgeComponent.findOneFullPath(ImageType.SHOES, shoes.getId());
        return ShoesResponse.of(shoes, fullPath);
    }

    /**
     * Buy Id로 Buy 데이터 조회
     *
     * @param id
     * @return BuyResponse
     */
    public BuyResponse findByBuyId(long id) {
        Buy orderRequest = buyRepository.findById(id).get();
        log.info("[BuyService.findById] id : " + orderRequest.getId());
        List<String> fullPath = imageBridgeComponent.findOneFullPath(ImageType.SHOES, orderRequest.getShoes().getId());
        return BuyResponse.of(orderRequest, fullPath);
    }

    /**
     * Shoes Id로 등록된 Buy 데이터 전체 조회
     *
     * @param id
     * @return List<BuyResponse>
     */
    public List<BuyResponse> findByShoes(long id) {
        List<BuyResponse> orderRequestList = new ArrayList<>();
        for (Buy orderRequest : buyRepository.findByShoesId(id)) {
            List<String> fullPath = imageBridgeComponent.findOneFullPath(ImageType.SHOES,
                    orderRequest.getShoes().getId());
            orderRequestList.add(BuyResponse.of(orderRequest, fullPath));
        }
        return orderRequestList;
    }

    /**
     * Shoes Id로 Size 정보가 있는 Sale 최저 가격 데이터 전체 조회
     *
     * @param id
     * @return BuySizeInfoResponse
     */
    public BuySizeInfoResponse findByShoesSizeMinPriceAll(Long id) {
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

    /**
     * Shoes Id와 Size로 Sale의 최저 가격 데이터 조회
     *
     * @param id
     * @param size
     * @return BuyFormResponse
     */
    public BuyFormResponse findByShoesSizeMinPriceOne(Long id, int size) {
        long price = 0L;
        Shoes shoes = shoesRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("조회된 신발이 없습니다."));

        Optional<Sale> lowestPriceSale = saleRepository.findLowestPriceSale(shoes, size);

        if (lowestPriceSale.isPresent()) {
            price = lowestPriceSale.get().getPrice();
        }

        List<String> fullPath = imageBridgeComponent.findOneFullPath(ImageType.SHOES,
                shoes.getId());

        return BuyFormResponse.of(shoes, size, price, fullPath);
    }

    /**
     * 구매 입찰 시 Buy 객체 생성
     *
     * @param buyRequest
     * @param member
     * @return BuyResponse
     */
    @Transactional
    public BuyResponse createBuyBid(CreateBuyRequest buyRequest, Member member) {
        Shoes shoes = shoesRepository.findById(buyRequest.getShoesId())
                .orElseThrow(() -> new IllegalArgumentException("조회된 신발이 없습니다."));

        ShoesSize shoesSize = shoesSizeRepository.findByShoesAndSize(shoes, buyRequest.getSize())
                .orElseThrow(() -> new IllegalArgumentException("조회된 사이즈 정보가 없습니다."));

        List<String> fullPath = imageBridgeComponent.findOneFullPath(ImageType.SHOES, shoes.getId());

        Address address = Address.builder()
                .address(buyRequest.getAddress())
                .postcode(buyRequest.getPostCode())
                .detailAddress(buyRequest.getDetailAddress())
                .extraAddress(buyRequest.getExtraAddress())
                .build();

        Buy buy = Buy.builder()
                .shoes(shoes)
                .shoesSize(shoesSize)
                .member(member)
                .price(buyRequest.getPrice())
                .receiverName(buyRequest.getReceiverName())
                .receiverPhoneNumber(buyRequest.getReceiverPhoneNumber())
                .receiverAddress(address.getFullAddress())
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .status(Status.BIDDING)
                .build();

        buyRepository.save(buy);

        return BuyResponse.of(buy, fullPath);
    }

    /**
     * 즉시 구매 시 Order 객체 생성
     *
     * @param buyRequest
     * @param member
     * @return OrderBuyResponse
     */
    @Transactional
    public OrderResponse createBuyNow(CreateBuyRequest buyRequest, Member member) {
        Shoes shoes = shoesRepository.findById(buyRequest.getShoesId())
                .orElseThrow(() -> new IllegalArgumentException("조회된 신발이 없습니다."));

        ShoesSize shoesSize = shoesSizeRepository.findByShoesAndSize(shoes, buyRequest.getSize())
                .orElseThrow(() -> new IllegalArgumentException("조회된 사이즈 정보가 없습니다."));

        List<String> fullPath = imageBridgeComponent.findOneFullPath(ImageType.SHOES, shoes.getId());

        Address address = Address.builder()
                .address(buyRequest.getAddress())
                .postcode(buyRequest.getPostCode())
                .detailAddress(buyRequest.getDetailAddress())
                .extraAddress(buyRequest.getExtraAddress())
                .build();

        Buy buy = Buy.builder()
                .shoes(shoes)
                .shoesSize(shoesSize)
                .member(member)
                .price(buyRequest.getPrice())
                .receiverName(buyRequest.getReceiverName())
                .receiverPhoneNumber(buyRequest.getReceiverPhoneNumber())
                .receiverAddress(address.getFullAddress())
                .startDate(LocalDate.now())
                .endDate(LocalDate.now())
                .status(Status.BIDDING)
                .build();

        buyRepository.save(buy);

        Sale sale = saleRepository.findLowestPriceSale(shoes, buyRequest.getSize())
                .orElseThrow(() -> new IllegalArgumentException("판매입찰 내역을 찾을 수 없습니다."));

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

        order.updateTossId(order.createTossId());
        return OrderResponse.of(order, fullPath);
    }

    public BuyResponse findById(long highestBidId) {
        Buy buy = buyRepository.findById(highestBidId)
                .orElseThrow(() -> new IllegalArgumentException("조회된 구매입찰 내역이 없습니다."));

        List<String> fullPath = imageBridgeComponent.findOneFullPath(ImageType.SHOES,
                buy.getShoes().getId());

        return BuyResponse.of(buy, fullPath);
    }

    public boolean confirmBuy(Member member, Long id, int size) {
//        Shoes shoes = shoesRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("조회된 신발이 없습니다."));
        Optional<Buy> findOne = buyRepository.findByShoesIdAndMemberAndShoesSizeSize(id, member
                , size);

        if (findOne.isEmpty()) {
            return true;
        }

        return false;
    }
}
