package com.ll.hype.domain.wishlist.wishlist.repository;

import static org.junit.jupiter.api.Assertions.*;

import com.ll.hype.domain.member.member.entity.Member;
import com.ll.hype.domain.member.member.repository.MemberRepository;
import com.ll.hype.domain.wishlist.wishlist.entity.Wishlist;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class WishlistRepositoryTest {

    @Autowired
    private WishlistRepository wishlistRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("회원으로 찜목록 조회")
    public void t1() {
        Member member = memberRepository.findById(1L).get();
        int result = 3;

        List<Wishlist> byMember = wishlistRepository.findByMember(member);

        assertThat(byMember.size()).isEqualTo(result);
    }
}