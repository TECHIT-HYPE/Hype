package com.ll.hype.global.s3.file.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.example.s3demo.s3.file.dto.S3FileDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


// 미해결 과제 1. NCP 스토리지에 저장할 때 Image 엔티티를 통해서 DB에 저장할건지?
// 2. 역시나 ImageBridge에도 저장할건지?
// 3. NCP에 저장후 리스트를 불러오는 매서드를 통해서 따로 저장할건지?
// 일단 3번이라고 가정하고 만듬
@RequiredArgsConstructor
@Service
public class FileUploadService {
    private final AmazonS3Client amazonS3Client;
    @Value("${spring.s3.bucket}")
    private String defaultBucketName;

    public String getUuidFileName(String fileName) {
        String ext = fileName.substring(fileName.indexOf(".") + 1); // 파일 확장자
        return UUID.randomUUID().toString() + "." + ext; // UUID + 파일확장자
    }

    //fileType 앞에 "/" 안해도 됨
    //fileType은 IMAGE_BRIDGE의 TYPE 테이블을 가져오면 좋을듯
    public List<S3FileDto> uploadFiles(List<MultipartFile> multipartFiles, String fileType) {
        List<S3FileDto> s3files = new ArrayList<>();

        for (MultipartFile multipartFile : multipartFiles) {
            String originalFileName = multipartFile.getOriginalFilename();
            String uploadFileName = getUuidFileName(originalFileName);
            String uploadFileUrl = "";

            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(multipartFile.getSize());
            objectMetadata.setContentType(multipartFile.getContentType());

            try (InputStream inputStream = multipartFile.getInputStream()) {
                //s3 map 구조 keyname, 파일, filetype (카테고리별)
                String keyName = fileType + "/" + uploadFileName;

                // S3에 폴더 및 파일 업로드
                amazonS3Client.putObject(
                        new PutObjectRequest(defaultBucketName, keyName, inputStream, objectMetadata)
                                .withCannedAcl(CannedAccessControlList.PublicRead));

                // S3에 업로드한 폴더 및 파일 URL
                uploadFileUrl = "https://kr.object.ncloudstorage.com/"+ defaultBucketName + "/" + keyName;

            } catch (IOException e) {
                e.printStackTrace();
            }

            s3files.add(
                    S3FileDto.builder()
                            .originalFileName(originalFileName)
                            .uploadFileName(uploadFileName)
                            .uploadFilePath(fileType)
                            .uploadFileUrl(uploadFileUrl)
                            .build());
        }

        return s3files;
    }
}
