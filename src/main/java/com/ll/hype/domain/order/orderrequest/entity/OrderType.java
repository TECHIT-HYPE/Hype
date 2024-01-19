package com.ll.hype.domain.order.orderrequest.entity;

public enum OrderType {
    ORDER("ORDER"), // 구매요청
    SALES("SALES"); // 판매요청
    private String value;

    OrderType(String value) {

    }
}
