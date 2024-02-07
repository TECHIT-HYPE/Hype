package com.ll.hype.domain.order.order.repository;

import com.ll.hype.domain.order.order.entity.Orders;
import java.util.Optional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class OrderRepositoryTest {
    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("tossId로 주문조회")
    public void t1() {
        String findTossId = "8-2024-02-07";
        Long resultId = 8L;

        Optional<Orders> byTossId = orderRepository.findByTossId(findTossId);
        Orders orders = byTossId.get();

        Assertions.assertThat(resultId).isEqualTo(orders.getId());
    }

}