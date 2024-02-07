package com.ll.hype.domain.order.order.entity;

public enum PaymentStatus {
    WAIT_PAYMENT, // 결제 대기
    COMPLETE_PAYMENT, // 결제 완료
    CANCEL_PAYMENT, // 결제 취소
    INCOMPLETE_DEPOSIT, // 미환불
    COMPLETE_DEPOSIT; // 환불 완료
}
