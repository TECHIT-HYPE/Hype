package com.ll.hype.global.s3.image.image.component;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ll.hype.global.s3.image.ImageType;
import com.ll.hype.global.s3.image.image.entity.Image;
import com.ll.hype.global.s3.image.image.repository.ImageRepository;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RequiredArgsConstructor
@Component
public class ImageComponent {
    private final AmazonS3Client amazonS3Client;
    private final ImageRepository imageRepository;
    @Value("${spring.s3.bucket}")
    private String defaultBucketName; //버킷이름은 소문자만 가능

    /**
     * 이미지와 타입 전달 시 이미지 엔티티 생성 및 버킷에 저장
     * @param multipartFile
     * @param type
     * @return Image
     */
    public Image upload(MultipartFile multipartFile, ImageType type) {
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new IllegalArgumentException("저장할 파일이 없습니다.");
        }

        String fileName = type + "/" +getUuidFileName(multipartFile);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        try{
            InputStream inputStream = multipartFile.getInputStream();

            amazonS3Client.putObject(
                    new PutObjectRequest(defaultBucketName, fileName, inputStream, objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead));

        } catch (IOException e) {
            throw new IllegalArgumentException("S3 파일 업로드 중 문제가 발생하였습니다.");
        }

        return Image.builder()
                .keyName(fileName)
                .build();
    }

    /**
     * Image 객체의 keyname을 통해 이미지의 fullPath를 생성하여 반환한다.
     * @param image
     * @return String (ImageFullPath)
     */
    public String convertPath(Image image) {
        return "https://kr.object.ncloudstorage.com/" + defaultBucketName + "/" + image.getKeyName();
    }

    /**
     * 이미지 객체 전달 시 엔티티 삭제 및 버킷에서 이미지를 삭제한다.
     * @param image
     * @return boolean
     */
    @Transactional
    public boolean delete(Image image) {
        String decodedKeyName = URLDecoder.decode(image.getKeyName(), StandardCharsets.UTF_8);

        // 파일 존재 여부 확인
        if (!amazonS3Client.doesObjectExist(defaultBucketName, decodedKeyName)) {
            throw new IllegalArgumentException("찾는 파일이 없습니다.");
        }

        amazonS3Client.deleteObject(defaultBucketName, decodedKeyName);
        imageRepository.delete(image);
        return true;
    }

    // 각 파일의 파일명을 고유하게 처리하도록 UUID를 통해 파일명 생성
    private String getUuidFileName(MultipartFile multipartFile) {
        String ext = Objects.requireNonNull(multipartFile.getContentType()).split("/")[1];
        return UUID.randomUUID() + "." + ext; // UUID + 파일확장자
    }
}

