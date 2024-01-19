package com.ll.hype.Hype.domain.order.order.entity;

public enum SettlementStatus {
    COMPLETE_PAYMENT("COMPLETE_PAYMENT"), // 결제 완료
    CANCEL_PAYMENT("CANCEL_PAYMENT"), // 결제 취소
    INCOMPLETE_DEPOSIT("INCOMPLETE_DEPOSIT"), // 미입금
    COMPLETE_DEPOSIT("COMPLETE_DEPOSIT"); // 입금 완료

    private String value;
    SettlementStatus(String value) {

    }
}
