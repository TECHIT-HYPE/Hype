package com.ll.hype.Hype.domain.order.transactionrequest.entity;

public enum OrderType {
    ORDER("ORDER"), // 구매요청
    SALES("SALES"); // 판매요청
    private String value;

    OrderType(String value) {

    }
}
