package com.ll.hype.global.s3.file.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3ObjectSummary;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//관리자가 이미지 파일 파악할 때 사용할 것 같은데 일단 서비스만 만들어 놓고 컨트롤러는 필요하면 구현하자
@Service
@RequiredArgsConstructor
public class FileListService {
    private final AmazonS3Client amazonS3Client;
    @Value("${spring.s3.bucket}")
    private String defaultBucketName;

    // list all in the bucket
    public List<String> listAllObjectsInBucket(String bucketName) {
        List<String> objectList = new ArrayList<>();

        try {
            ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
                    .withBucketName(bucketName)
                    .withMaxKeys(300);

            ObjectListing objectListing = amazonS3Client.listObjects(listObjectsRequest);

            while (true) {
                for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                    objectList.add(objectSummary.getKey());
                }

                if (objectListing.isTruncated()) {
                    objectListing = amazonS3Client.listNextBatchOfObjects(objectListing);
                } else {
                    break;
                }
            }
        } catch (AmazonS3Exception e) {
            System.err.println(e.getErrorMessage());
            // 예외 처리 필요한 경우 처리
        }

        return objectList;
    }

    public Map<String, List<String>> listTopLevelFoldersAndFiles(String bucketName) {
        Map<String, List<String>> folderAndFileMap = new LinkedHashMap<>();

        try {
            ListObjectsRequest listObjectsRequest = new ListObjectsRequest()
                    .withBucketName(bucketName)
                    .withDelimiter("/")
                    .withMaxKeys(300);

            ObjectListing objectListing = amazonS3Client.listObjects(listObjectsRequest);

            for (String commonPrefixes : objectListing.getCommonPrefixes()) {
                folderAndFileMap.put(commonPrefixes, new ArrayList<>());
            }

            for (S3ObjectSummary objectSummary : objectListing.getObjectSummaries()) {
                String key = objectSummary.getKey();
                String folder = key.substring(0, key.lastIndexOf('/') + 1);
                String fileName = key.substring(key.lastIndexOf('/') + 1);

                if (folderAndFileMap.containsKey(folder)) {
                    folderAndFileMap.get(folder).add(fileName);
                }
            }
        } catch (AmazonS3Exception e) {
            e.printStackTrace();
            // 예외 처리 필요한 경우 처리
        } catch (SdkClientException e) {
            e.printStackTrace();
            // 예외 처리 필요한 경우 처리
        }

        return folderAndFileMap;
    }
}

