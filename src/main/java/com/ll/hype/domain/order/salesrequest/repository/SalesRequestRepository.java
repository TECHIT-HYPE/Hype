package com.ll.hype.domain.order.salesrequest.repository;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.order.salesrequest.entity.SalesRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalesRequestRepository extends JpaRepository<SalesRequest, Long> {

}
