package com.ll.hype.domain.order.sale.dto.request;

import com.ll.hype.global.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateSaleRequest {
    private Long shoesId;
    private int size;
    private Long price;
    private int endDate;

    private String address;
    private String account;
}
