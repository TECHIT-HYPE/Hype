package com.ll.hype.global.s3.bucket.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BucketCreationService {

    private final AmazonS3Client amazonS3Client;

    @Value("${spring.s3.bucket}")
    private String defaultBucketName;

    public void createBucket(String bucketName) {
    try {
        // create bucket if the bucket name does not exist
        if (amazonS3Client.doesBucketExistV2(defaultBucketName)) {
            System.out.format("Bucket %s already exists.\n", defaultBucketName);
        } else {
            amazonS3Client.createBucket(defaultBucketName);
            System.out.format("Bucket %s has been created.\n", defaultBucketName);
        }
    } catch (
    AmazonS3Exception e) {
        e.printStackTrace();
    } catch(
    SdkClientException e) {
        e.printStackTrace();
    }
    }
}
