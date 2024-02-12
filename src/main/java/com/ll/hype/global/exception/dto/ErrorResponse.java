package com.ll.hype.global.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private Long errorCode;
    private String errorMsg;
    private String errorType;

    public static ErrorResponse of(Long errorCode, Exception e) {
        return ErrorResponse.builder()
                .errorCode(errorCode)
                .errorMsg(e.getMessage())
                .errorType(e.getClass().getSimpleName())
                .build();
    }
}
