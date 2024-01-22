package com.ll.hype.global.s3.file.controller;

import com.ll.hype.global.s3.file.service.FileDeleteService;
import com.ll.hype.global.s3.file.service.FileUploadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class FileController {
    private final FileUploadService fileUploadService;
    private final FileDeleteService fileDeleteService;

    @GetMapping("/upload")
    public String getUpload() {
        return "upload";
    }

    @PostMapping("/upload")
    public ResponseEntity<Object> uploadFile(
            @RequestPart(value = "files") List<MultipartFile> multipartFiles,
            @RequestParam(value = "fileType") String fileType) {
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(fileUploadService.uploadFiles(multipartFiles, fileType));
    }
    @GetMapping("/delete")
    public String getDelete() {return "delete";}

    @PostMapping("/delete")
    public ResponseEntity<Object> deleteFile(@RequestParam(value = "keyName") String keyName) {
        fileDeleteService.deleteFile(keyName);
        return ResponseEntity.status(HttpStatus.OK).body("File deleted successfully");
    }
}
