package com.ll.hype.domain.adress.adress.service;

import com.ll.hype.domain.adress.adress.dto.AddressResponse;
import com.ll.hype.domain.adress.adress.entity.Address;
import com.ll.hype.domain.adress.adress.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AddressService {
    private final AddressRepository addressRepository;

    public List<AddressResponse> getMyAddressList(Long memberId) {
        List<AddressResponse> addressList = new ArrayList<>();
        for (Address address : addressRepository.findByMemberIdOrderByIsPrimaryDescCreateDateDesc(memberId)) {
            addressList.add(AddressResponse.of(address));
        }
        return addressList;
    }
}
