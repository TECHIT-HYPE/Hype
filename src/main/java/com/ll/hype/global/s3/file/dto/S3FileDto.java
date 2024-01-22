package com.ll.hype.global.s3.file.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@Builder
public class S3FileDto {
    private String originalFileName; // 실제 파일명
    private String uploadFileName; //오브젝트 스토리지에 저장될 이름
    private String uploadFilePath; // 클라이언트에서 올릴 파일의 경로
    private String uploadFileUrl;
}
