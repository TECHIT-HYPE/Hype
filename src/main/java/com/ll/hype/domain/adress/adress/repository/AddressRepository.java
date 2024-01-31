package com.ll.hype.domain.adress.adress.repository;

import com.ll.hype.domain.adress.adress.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByMemberIdOrderByIsPrimaryDescCreateDateDesc(Long memberId);
}
