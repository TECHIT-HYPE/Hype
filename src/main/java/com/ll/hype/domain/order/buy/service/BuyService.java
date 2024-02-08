package com.ll.hype.domain.order.buy.service;

import com.ll.hype.domain.address.address.entity.Address;
import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.order.buy.dto.response.BuyFormResponse;
import com.ll.hype.domain.order.buy.dto.request.CreateBuyRequest;
import com.ll.hype.domain.order.buy.dto.response.BuyModifyPriceResponse;
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
import com.ll.hype.global.exception.custom.EntityNotFoundException;
import com.ll.hype.global.s3.image.ImageType;
import com.ll.hype.global.s3.image.imagebridge.component.ImageBridgeComponent;
import com.ll.hype.domain.order.buy.util.validate.BuyValidator;
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

    /**
     * Shoes Id로 Shoes 조회
     *
     * @param shoesId
     * @return
     */
    public ShoesResponse findByShoesId(Long shoesId) {
        Shoes shoes = shoesRepository.findById(shoesId)
                .orElseThrow(() -> new EntityNotFoundException("조회된 객체가 없습니다."));

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
        Buy orderRequest = buyRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("조회된 구매 내역이 없습니다."));
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
    public BuySizeInfoResponse findByShoesSizeMinPriceAll(Long id, Member member) {
        Shoes shoes = shoesRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("조회된 신발이 없습니다."));
        List<Sale> findByMinPrice = saleRepository.findLowestPriceByShoesId(shoes, member);
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
    public BuyFormResponse findByShoesSizeMinPriceOne(Long id, int size, Member member) {
        long price = 0L;
        Shoes shoes = shoesRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("조회된 신발이 없습니다."));

        Optional<Sale> opSale = saleRepository.findLowestPriceSale(shoes, size, member);

        if (opSale.isPresent()) {
            price = opSale.get().getPrice();
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
                .orElseThrow(() -> new EntityNotFoundException("조회된 신발이 없습니다."));

        ShoesSize shoesSize = shoesSizeRepository.findByShoesAndSize(shoes, buyRequest.getSize())
                .orElseThrow(() -> new EntityNotFoundException("조회된 사이즈 정보가 없습니다."));

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
     * 입찰 페이지에서 즉시 구매 시 Order 객체 생성
     *
     * @param buyRequest
     * @param member
     * @return OrderBuyResponse
     */
    @Transactional
    public OrderResponse createBuyNow(CreateBuyRequest buyRequest, Member member) {
        Shoes shoes = shoesRepository.findById(buyRequest.getShoesId())
                .orElseThrow(() -> new EntityNotFoundException("조회된 신발이 없습니다."));

        ShoesSize shoesSize = shoesSizeRepository.findByShoesAndSize(shoes, buyRequest.getSize())
                .orElseThrow(() -> new EntityNotFoundException("조회된 사이즈 정보가 없습니다."));

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

        Sale sale = saleRepository.findLowestPriceSale(shoes, buyRequest.getSize(), member)
                .orElseThrow(() -> new EntityNotFoundException("판매입찰 내역을 찾을 수 없습니다."));

        BuyValidator.canNotMyDataOrder(sale, member);
        BuyValidator.checkBuyPriceEqualsSalePrice(buy.getPrice(), sale.getPrice());

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

    /**
     * BuyId로 데이터 조회
     *
     * @param buyId
     * @return BuyResponse
     */
    public BuyResponse findById(long buyId) {
        Buy buy = buyRepository.findById(buyId)
                .orElseThrow(() -> new EntityNotFoundException("조회된 구매입찰 내역이 없습니다."));

        List<String> fullPath = imageBridgeComponent.findOneFullPath(ImageType.SHOES,
                buy.getShoes().getId());

        return BuyResponse.of(buy, fullPath);
    }

    /**
     * 사이즈 선택 시 이미 자신이 입찰 중인 상품인지 확인
     *
     * @param member
     * @param id
     * @param size
     * @return boolean
     */
    public boolean confirmBuy(Member member, Long id, int size) {
        Optional<Buy> findOne = buyRepository.findByShoesIdAndMemberAndShoesSizeSizeAndStatus(id, member, size, Status.BIDDING);
        return findOne.isEmpty();
    }

    /**
     * 입찰 금액 수정 폼 데이터 전송
     *
     * @param id
     * @param member
     * @return BuyModifyPriceResponse
     */
    public BuyModifyPriceResponse findBuyIdAndMember(Long id, Member member) {
        Buy buy = buyRepository.findByIdAndMember(id, member)
                .orElseThrow(() -> new EntityNotFoundException("조회된 구매 입찰 내역이 없습니다."));

        BuyValidator.checkUserMatch(buy, member);

        Long minPrice = 0L;
        Optional<Sale> findSale =
                saleRepository.findLowestPriceSale(buy.getShoes(), buy.getShoesSize().getSize(), member);

        if (findSale.isPresent()) {
            minPrice = findSale.get().getPrice();
        }

        return BuyModifyPriceResponse.of(buy, minPrice);
    }

    /**
     * 입찰 금액 수정
     *
     * @param id
     * @param price
     * @param member
     */
    @Transactional
    public void updatePrice(Long id, Long price, Member member) {
        Buy buy = buyRepository.findByIdAndMember(id, member)
                .orElseThrow(() -> new EntityNotFoundException("조회된 구매 입찰 내역이 없습니다."));
        BuyValidator.checkUserMatch(buy, member);
        buy.updatePrice(price);
    }

    /**
     * 구매 입찰 삭제
     *
     * @param id
     * @param member
     */
    @Transactional
    public void deleteBuy(Long id, Member member) {
        Buy buy = buyRepository.findByIdAndMember(id, member)
                .orElseThrow(() -> new EntityNotFoundException("조회된 구매 입찰 내역이 없습니다."));
        BuyValidator.checkUserMatch(buy, member);
        buyRepository.delete(buy);
    }

    /**
     * 입찰 금액 수정페이지 내 즉시 구매 기능
     *
     * @param id
     * @param nowPrice
     * @param member
     * @return OrderBuyResponse
     */
    @Transactional
    public OrderResponse createModifyBuyNow(Long id, Long nowPrice, Member member) {
        Buy buy = buyRepository.findByIdAndMember(id, member)
                .orElseThrow(() -> new EntityNotFoundException("조회된 구매 입찰 내역이 없습니다."));

        BuyValidator.checkUserMatch(buy, member);

        Sale sale = saleRepository.findLowestPriceSale(buy.getShoes(), buy.getShoesSize().getSize(), member)
                .orElseThrow(() -> new EntityNotFoundException("판매입찰 내역을 찾을 수 없습니다."));

        BuyValidator.canNotMyDataOrder(sale, member);
        BuyValidator.checkBuyPriceEqualsSalePrice(nowPrice, sale.getPrice());

        buy.updatePrice(nowPrice);

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

        List<String> fullPath = imageBridgeComponent.findOneFullPath(ImageType.SHOES, buy.getShoes().getId());
        return OrderResponse.of(order, fullPath);
    }
}
