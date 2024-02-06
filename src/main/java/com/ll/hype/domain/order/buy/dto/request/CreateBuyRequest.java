package com.ll.hype.domain.order.buy.dto.request;

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

    private String postCode;
    private String address;
    private String detailAddress;
    private String extraAddress;
}
