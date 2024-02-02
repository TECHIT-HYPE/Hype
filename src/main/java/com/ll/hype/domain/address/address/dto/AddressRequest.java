package com.ll.hype.domain.address.address.dto;

import com.ll.hype.domain.address.address.entity.Address;
import com.ll.hype.domain.member.member.entity.Member;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequest {
    private String addressName;

    private String postcode;

    private String address;

    private String detailAddress;

    private String extraAddress;

    private boolean isPrimary;

    public static Address toEntity(AddressRequest addressRequest, Member member) {
        return Address.builder()
                .member(member)
                .addressName(addressRequest.addressName)
                .postcode(addressRequest.postcode)
                .address(addressRequest.address)
                .detailAddress(addressRequest.detailAddress)
                .extraAddress(addressRequest.extraAddress)
                .isPrimary(addressRequest.isPrimary)
                .build();
    }
}
