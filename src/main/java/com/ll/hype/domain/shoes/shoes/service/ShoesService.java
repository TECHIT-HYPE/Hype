package com.ll.hype.domain.shoes.shoes.service;


import com.ll.hype.domain.shoes.shoes.dto.ShoesRequest;
import com.ll.hype.domain.shoes.shoes.dto.ShoesResponse;
import com.ll.hype.domain.shoes.shoes.entity.Shoes;
import com.ll.hype.domain.shoes.shoes.repository.ShoesRepository;
import com.ll.hype.global.enums.StatusCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ShoesService {
    private final ShoesRepository shoesRepository;
    public ShoesResponse findById(long id) {
        Shoes shoes = shoesRepository.findById(id).get();
        return ShoesResponse.of(shoes);
    }

    public List<ShoesResponse> findAll() {
        List<ShoesResponse> shoesList = new ArrayList<>();
        for (Shoes shoes : shoesRepository.findByStatus(StatusCode.ENABLE)) {
            shoesList.add(ShoesResponse.of(shoes));
        }
        return shoesList;
    }

}
