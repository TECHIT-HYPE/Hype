package com.ll.hype.domain.salesrequest.sales.entity;

public enum Status {
    BIDDING("BIDDING"),
    EXPIRED("EXPIRED"),
    FAILED("FAILED"),
    COMPLETE("COMPLETE");
    private String value;
    Status(String value) {

    }

}