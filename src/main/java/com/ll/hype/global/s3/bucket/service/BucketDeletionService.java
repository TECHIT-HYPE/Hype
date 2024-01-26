package com.ll.hype.global.s3.bucket.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Iterator;

@RequiredArgsConstructor
@Service
public class BucketDeletionService {
    private final AmazonS3Client amazonS3Client;
    @Value("${spring.s3.bucket}")
    private String defaultBucketName;

    public void deleteBucket() {
        try {
            // delete bucket if the bucket exists
            if (amazonS3Client.doesBucketExistV2(defaultBucketName)) {
                // delete all objects
                ObjectListing objectListing = amazonS3Client.listObjects(defaultBucketName);
                while (true) {
                    for (Iterator<?> iterator = objectListing.getObjectSummaries().iterator(); iterator.hasNext();) {
                        S3ObjectSummary summary = (S3ObjectSummary)iterator.next();
                        amazonS3Client.deleteObject(defaultBucketName, summary.getKey());
                    }

                    if (objectListing.isTruncated()) {
                        objectListing = amazonS3Client.listNextBatchOfObjects(objectListing);
                    } else {
                        break;
                    }
                }

                // abort incomplete multipart uploads
                MultipartUploadListing multipartUploadListing = amazonS3Client.listMultipartUploads(new ListMultipartUploadsRequest(defaultBucketName));
                while (true) {
                    for (Iterator<?> iterator = multipartUploadListing.getMultipartUploads().iterator(); iterator.hasNext();) {
                        MultipartUpload multipartUpload = (MultipartUpload)iterator.next();
                        amazonS3Client.abortMultipartUpload(new AbortMultipartUploadRequest(defaultBucketName, multipartUpload.getKey(), multipartUpload.getUploadId()));
                    }

                    if (multipartUploadListing.isTruncated()) {
                        ListMultipartUploadsRequest listMultipartUploadsRequest = new ListMultipartUploadsRequest(defaultBucketName);
                        listMultipartUploadsRequest.withUploadIdMarker(multipartUploadListing.getNextUploadIdMarker());
                        multipartUploadListing = amazonS3Client.listMultipartUploads(listMultipartUploadsRequest);
                    } else {
                        break;
                    }
                }

                amazonS3Client.deleteBucket(defaultBucketName);
                System.out.format("Bucket %s has been deleted.\n", defaultBucketName);
            } else {
                System.out.format("Bucket %s does not exist.\n", defaultBucketName);
            }
        } catch (AmazonS3Exception e) {
            e.printStackTrace();
        } catch(SdkClientException e) {
            e.printStackTrace();
        }
    }
}
