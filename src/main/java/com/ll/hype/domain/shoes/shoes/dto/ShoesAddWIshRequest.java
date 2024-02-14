package com.ll.hype.domain.shoes.shoes.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestBody;

@Getter
@Setter
public class ShoesAddWIshRequest {
    private Long shoesId;
    private int size;
}
