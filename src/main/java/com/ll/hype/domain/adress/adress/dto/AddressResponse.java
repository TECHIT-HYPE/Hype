package com.ll.hype.domain.adress.adress.dto;

import com.ll.hype.domain.adress.adress.entity.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponse {
    private Long id;
    private Long memberId;
    private String addressName;
    private String fullAddress;
    private boolean isPrimary;

    public static AddressResponse of(Address address) {
        return AddressResponse.builder()
                .id(address.getId())
                .memberId(address.getMember().getId())
                .addressName(address.getAddressName())
                .fullAddress(address.getFullAddress())
                .isPrimary(address.isPrimary())
                .build();
    }
}
