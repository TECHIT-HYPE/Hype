package com.ll.hype.domain.shoes.shoes.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ShoesSizeDTO {
    private int size;
    private boolean exists;
    private long price;
}
