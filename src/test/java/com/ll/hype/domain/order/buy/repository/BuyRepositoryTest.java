package com.ll.hype.domain.order.buy.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.member.member.repository.MemberRepository;
import com.ll.hype.domain.order.buy.entity.Buy;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BuyRepositoryTest {
    @Autowired
    private BuyRepository buyRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("Buy Id And Member == buy")
    public  void t1() {
        Long id = 4L;
        Member member = memberRepository.findById(1L).get();

        Long resultId = 4L;
        Long shoesId = 1L;

        Buy buy = buyRepository.findByIdAndMember(id, member).get();

        assertThat(buy.getId()).isEqualTo(resultId);
        assertThat(buy.getShoes().getId()).isEqualTo(shoesId);
    }

}