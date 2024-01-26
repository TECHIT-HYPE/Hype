package com.ll.hype.domain.order.buy.entity;

public enum BuyType {
    ORDER("ORDER"), // 구매요청
    SALES("SALES"); // 판매요청
    private String value;

    BuyType(String value) {

    }
}
