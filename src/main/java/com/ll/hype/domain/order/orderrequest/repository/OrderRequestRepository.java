package com.ll.hype.domain.order.orderrequest.repository;

import com.ll.hype.domain.order.orderrequest.entity.OrderRequest;
import com.ll.hype.domain.order.salesrequest.entity.SalesRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRequestRepository extends JpaRepository<OrderRequest, Long> {
    List<OrderRequest> findByShoesId(Long id);

}
