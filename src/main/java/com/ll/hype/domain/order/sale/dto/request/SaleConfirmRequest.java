package com.ll.hype.domain.order.sale.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaleConfirmRequest {
    private long shoesId;
    private int size;
}
