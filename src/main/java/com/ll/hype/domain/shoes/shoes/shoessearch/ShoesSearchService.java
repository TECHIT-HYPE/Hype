package com.ll.hype.domain.shoes.shoes.shoessearch;

import com.ll.hype.domain.shoes.shoes.dto.ShoesResponse;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.domain.shoes.shoes.repository.ShoesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class ShoesSearchService {
    private final ShoesRepository shoesRepository;

    // 해야하는것 :  모델명 , 브랜드(한국, 영어 2개있다)
    // 신발 한국 이름 조회
    public List<ShoesResponse> findByKeyword(String keyword) {
        List<ShoesResponse> shoess = new ArrayList<>();
        List<Shoes> findByKorNames = shoesRepository.findByKorNameContainingIgnoreCase(keyword);
        for (Shoes shoes : findByKorNames) {
            shoess.add(ShoesResponse.of(shoes));
        }
        return shoess;
    }

    // 신발 영어 이름 조회
//    public List<ShoesResponse> findByEngword(String keyword) {
//        List<ShoesResponse> shoess = new ArrayList<>();
//        List<Shoes> findByEngNames = shoesRepository.findByEngNameContainingIgnoreCase(keyword);
//        for (Shoes shoes : findByEngNames) {
//            shoess.add(ShoesResponse.of(shoes));
//        }
//        return shoess;
//    }

}

