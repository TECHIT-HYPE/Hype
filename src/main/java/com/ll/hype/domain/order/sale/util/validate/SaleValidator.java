package com.ll.hype.domain.order.sale.util.validate;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.order.buy.entity.Buy;
import com.ll.hype.domain.order.sale.entity.Sale;
import com.ll.hype.global.exception.custom.UserMismatchException;

public class SaleValidator {
    // 판매자와 현재 로그인 정보가 일치하는지 확인
    public static void checkUserMatch(Sale sale, Member member) {
        if (!sale.getMember().getId().equals(member.getId())) {
            throw new UserMismatchException("회원정보가 일치하지 않습니다.");
        }
    }

    // sale과 buy의 거래 금액이 일치하는지 확인
    public static void checkSalePriceEqualsBuyPrice(Long buyPrice, Long salePrice) {
        if (!buyPrice.equals(salePrice)) {
            throw new IllegalArgumentException("거래 성사 금액이 일치하지 않습니다.");
        }
    }

    // 나의 구매 입찰 데이터인지 확인
    public static void canNotMyDataOrder(Buy buy, Member member) {
        if (buy.getMember().getId().equals(member.getId())) {
            throw new IllegalArgumentException("나의 구매 입찰 데이터와는 거래가 불가합니다.");
        }
    }

}
