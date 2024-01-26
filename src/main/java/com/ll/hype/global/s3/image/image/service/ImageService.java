package com.ll.hype.global.s3.image.image.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.ll.hype.global.s3.image.ImageType;
import com.ll.hype.global.s3.image.image.entity.Image;
import com.ll.hype.global.s3.image.image.repository.ImageRepository;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@Slf4j
@Service
public class ImageService {
    private final AmazonS3Client amazonS3Client;

    @Value("${spring.s3.bucket}")
    private String defaultBucketName;

    private final ImageRepository imageRepository;

    //fileType 앞에 "/" 안해도 됨
    //fileType은 IMAGE_BRIDGE의 TYPE 테이블을 가져오면 좋을듯
    @Transactional
    public Image uploadImage(MultipartFile multipartFile, ImageType imageType) {
        String originalFileName = multipartFile.getOriginalFilename();
        String uploadFileName = getUuidFileName(originalFileName);

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.setContentLength(multipartFile.getSize());
        objectMetadata.setContentType(multipartFile.getContentType());

        //s3 map 구조 keyname, 파일, filetype (카테고리별)
        String keyName = ImageType.getTypeName(imageType) + "/" + uploadFileName;

        try (InputStream inputStream = multipartFile.getInputStream()) {

            // S3에 폴더 및 파일 업로드
            amazonS3Client.putObject(
                    new PutObjectRequest(defaultBucketName, keyName, inputStream, objectMetadata)
                            .withCannedAcl(CannedAccessControlList.PublicRead));

            // S3에 업로드한 폴더 및 파일 URL
            // uploadFileUrl = "https://kr.object.ncloudstorage.com/" + defaultBucketName + "/" + keyName;

        } catch (IOException e) {
            e.printStackTrace();
        }

        Image image = Image.builder()
                .keyName(keyName)
                .build();

        imageRepository.save(image);

        return image;
    }

    @Transactional
    public boolean removeImage(long id) {
        try {
            Optional<Image> findOne = imageRepository.findById(id);

            if (findOne.isEmpty()) {
                return false;
            }

            String decodedKeyName = URLDecoder.decode(findOne.get().getKeyName(), "UTF-8");

            // 파일 존재 여부 확인
            if (amazonS3Client.doesObjectExist(defaultBucketName, decodedKeyName)) {
                amazonS3Client.deleteObject(defaultBucketName, decodedKeyName);
                log.info("Object {} in bucket {} has been deleted.", decodedKeyName, defaultBucketName);
                imageRepository.deleteById(id);

            } else {
                log.warn("Object {} in bucket {} does not exist.", decodedKeyName, defaultBucketName);
            }

            return true;

        } catch (AmazonS3Exception e) {
            log.error("Error deleting object from S3: {}", e.getMessage());
            return false;
        } catch (SdkClientException | UnsupportedEncodingException e) {
            log.error("Error in S3 client: {}", e.getMessage());
            return false;
        }
    }

    private String getUuidFileName(String fileName) {
        String ext = fileName.substring(fileName.indexOf(".") + 1); // 파일 확장자
        return UUID.randomUUID() + "." + ext; // UUID + 파일확장자
    }

    public String findFullPath(long id) {
        Optional<Image> findOne = imageRepository.findById(id);
        if (!findOne.isEmpty()) {
            return null;
        }

        String fullPath = "https://kr.object.ncloudstorage.com/" + defaultBucketName + "/" + findOne.get().getKeyName();
        return fullPath;
    }
}

