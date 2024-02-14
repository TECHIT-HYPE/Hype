package com.ll.hype.domain.order.order.util.validate;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.order.order.entity.Orders;
import com.ll.hype.domain.order.sale.entity.Sale;
import com.ll.hype.global.exception.custom.UserMismatchException;

public class OrderValidator {

    //Order Member 정보와 현재 로그인 정보가 일치하는지 확인
    public static void checkUserMatch(Orders order, Member member) {
        if (!order.getSale().getMember().getId().equals(member.getId())) {
            throw new UserMismatchException("회원정보가 일치하지 않습니다.");
        }
    }
}
