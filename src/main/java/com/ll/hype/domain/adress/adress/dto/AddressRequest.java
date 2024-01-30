package com.ll.hype.domain.adress.adress.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {
    private String addressName;

    private String postcode;

    private String address;

    private String detailAddress;

    private String extraAddress;
}
