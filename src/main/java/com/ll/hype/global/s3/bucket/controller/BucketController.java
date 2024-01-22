package com.ll.hype.global.s3.bucket.controller;

import com.ll.hype.global.s3.bucket.service.BucketCreationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/create")
    public ResponseEntity<String> createBucket() {
        bucketCreationService.createBucket();
        return ResponseEntity.ok("Bucket creation request submitted.");
    }
}
