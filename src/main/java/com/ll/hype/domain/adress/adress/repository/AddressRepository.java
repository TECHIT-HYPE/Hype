package com.ll.hype.domain.adress.adress.repository;

import com.ll.hype.domain.adress.adress.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByMemberIdOrderByIsPrimaryDescCreateDateDesc(Long memberId);

    @Modifying
    @Query("UPDATE Address a SET a.isPrimary = false, a.modifyDate = now() " +
            "WHERE a.member.id = :memberId and a.isPrimary = true")
    void updateIsPrimary(Long memberId);
}
