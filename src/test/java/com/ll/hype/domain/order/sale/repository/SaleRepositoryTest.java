package com.ll.hype.domain.order.sale.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.member.member.repository.MemberRepository;
import com.ll.hype.domain.order.sale.entity.Sale;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.domain.shoes.shoes.repository.ShoesRepository;
import com.ll.hype.global.enums.Status;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SaleRepositoryTest {
    @Autowired
    private SaleRepository saleRepository;

    @Autowired
    private ShoesRepository shoesRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("sale 최저가 1개 조회")
    public void t1() {
        Shoes shoes = shoesRepository.findById(1L).get();
        Member member = memberRepository.findById(1L).get();
        int size = 260;

        Sale sale = saleRepository.findLowestPriceSale(shoes, size, member).get();

        System.out.println("sale.getStatus() : " + sale.getStatus());

        sale.updateStatus(Status.BID_COMPLETE);
        saleRepository.saveAndFlush(sale);

        Sale sale2 = saleRepository.findLowestPriceSale(shoes, size, member).get();
        System.out.println("sale2.getStatus() : " + sale2.getStatus());


    }
}