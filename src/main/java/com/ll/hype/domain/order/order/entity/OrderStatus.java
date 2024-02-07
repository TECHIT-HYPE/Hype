package com.ll.hype.domain.order.order.entity;

public enum OrderStatus {
    TRADING, // 거래 중
    TRAD_COMPLETE, // 거래 완료
    TRAD_CANCEL, // 거래 취소
    TRAD_RETURNING, // 반품 진행 중
    TRAD_RETURN_COMPLETE; // 반품 완료
}
