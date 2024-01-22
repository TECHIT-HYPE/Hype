package com.ll.hype.global.s3.file.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

//역시나 NCP 에서 파일 삭제시 Image, ImageBridge에서 삭제할 건지? 삭제해야됨
// String keyName = fileType + "/" + UUID + 파일확장자;
@Service
@RequiredArgsConstructor
public class FileDeleteService {
    private final AmazonS3Client amazonS3Client;
    @Value("${spring.s3.bucket}")
    private String defaultBucketName;

    private static final Logger logger = LoggerFactory.getLogger(FileDeleteService.class);

    public void deleteFile(String keyName) {
        try {
            String decodedKeyName = URLDecoder.decode(keyName, "UTF-8");

            // 파일 존재 여부 확인
            if (amazonS3Client.doesObjectExist(defaultBucketName, decodedKeyName)) {
                amazonS3Client.deleteObject(defaultBucketName, decodedKeyName);
                logger.info("Object {} in bucket {} has been deleted.", decodedKeyName, defaultBucketName);
            } else {
                logger.warn("Object {} in bucket {} does not exist.", decodedKeyName, defaultBucketName);
            }

        } catch (AmazonS3Exception e) {
            logger.error("Error deleting object from S3: {}", e.getMessage());
            throw new AmazonS3Exception(e.getMessage());
        } catch (SdkClientException | UnsupportedEncodingException e) {
            logger.error("Error in S3 client: {}", e.getMessage());
            throw new SdkClientException(e.getMessage());
        }
    }
}

