package com.ll.hype.domain.order.order.entity;

public enum OrderStatus {
    TRADING, // 거래 중
    TRADE_COMPLETE, // 거래 완료
    TRADE_CANCEL, // 거래 취소
    TRADE_RETURNING, // 반품 진행 중
    TRADE_RETURN_COMPLETE; // 반품 완료
}
