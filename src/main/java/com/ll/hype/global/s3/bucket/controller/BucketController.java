package com.ll.hype.global.s3.bucket.controller;

import com.ll.hype.global.s3.bucket.service.BucketCreationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bucket")
public class BucketController {
    private final BucketCreationService bucketCreationService;

    @Value("${spring.s3.bucket}")
    private String defaultBucketName;

    @PostMapping("/create")
    public ResponseEntity<String> createBucket() {
        bucketCreationService.createBucket(defaultBucketName);
        return ResponseEntity.ok("Bucket creation request submitted.");
    }
}
