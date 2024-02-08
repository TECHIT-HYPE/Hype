package com.ll.hype.domain.order.buy.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BuyConfirmRequest {
    private long shoesId;
    private int size;
}
