package com.ll.hype.domain.order.buy.util.validate;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.order.buy.entity.Buy;
import com.ll.hype.domain.order.sale.entity.Sale;
import com.ll.hype.global.exception.custom.UserMismatchException;

public class BuyValidator {
    /**
     * Buy Member와 현재 로그인 정보가 일치하는지 확인
     */
    public static void checkUserMatch(Buy buy, Member member) {
        if (!buy.getMember().getId().equals(member.getId())) {
            throw new UserMismatchException("회원정보가 일치하지 않습니다.");
        }
    }

    /**
     * Buy의 거래 금액과 Sale의 거래 금액이 일치하는지 확인
     */
    public static void checkBuyPriceEqualsSalePrice(Long buyPrice, Long salePrice) {
        if (!buyPrice.equals(salePrice)) {
            throw new IllegalArgumentException("거래 성사 금액이 일치하지 않습니다.");
        }
    }

    /**
     * 내가 등록한 판매 입찰 데이터인지 확인
     */
    public static void canNotMyDataOrder(Sale sale, Member member) {
        if (sale.getMember().getId().equals(member.getId())) {
            throw new IllegalArgumentException("나의 판매 입찰 데이터와는 거래가 불가합니다.");
        }
    }

}
