package com.ll.hype.domain.social.social.repository;

import com.ll.hype.domain.social.social.entity.SocialShoes;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SocialShoesRepository extends JpaRepository<SocialShoes, Long> {
    List<SocialShoes> findAllBySocial_Id(Long socialId);
}
