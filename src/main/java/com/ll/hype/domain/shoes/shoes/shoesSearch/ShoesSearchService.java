package com.ll.hype.domain.shoes.shoes.shoesSearch;

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

    public List<ShoesResponse> findByKeyword(String keyword) {
        List<ShoesResponse> shoess = new ArrayList<>();
        List<Shoes> findByKorNames = shoesRepository.findByKorNameContainingIgnoreCase(keyword);
        for (Shoes shoes : findByKorNames) {
            shoess.add(ShoesResponse.of(shoes));
        }
        return shoess;
    }
}
