package com.ll.hype.domain.order.buy.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateBuyRequest {
    private Long shoesId;
    private int size;
    private Long price;
    private int endDate;
}
