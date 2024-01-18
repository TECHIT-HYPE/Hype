package com.ll.hype.domain.order.order.entity;

public enum SettlementStatus {
    COMPLETE_PAYMENT("COMPLETE_PAYMENT"),
    CANCEL_PAYMENT("CANCEL_PAYMENT"),
    INCOMPLETE_DEPOSIT("INCOMPLETE_DEPOSIT"),
    COMPLETE_DEPOSIT("COMPLETE_DEPOSIT");

    private String value;
    SettlementStatus(String value) {

    }
}
