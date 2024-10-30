package com.bwmanager.jaegwan.global.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.DeleteObjectRequest;
import software.amazon.awssdk.services.s3.model.GetUrlRequest;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Util {
    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    @Value("${cloud.aws.s3.prefix}")
    private String prefix;

    @Async
    public CompletableFuture<String> upload(MultipartFile multipartFile, String filename) throws IOException {
        // 원래 파일 이름에서 확장자를 추출합니다.
        String originalFilename = multipartFile.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        // 새 파일 이름에 확장자를 추가합니다.
        String key = prefix + "/" + filename + extension;
        try {
            putS3(multipartFile, key);
            return CompletableFuture.completedFuture(findUploadKeyUrl(key));
        } catch (S3Exception e) {
            log.error("Error uploading file to S3: {}", e.getMessage());
            throw e;
        }
    }

    // 실제 업로드 하는 메소드
    private String putS3(MultipartFile file, String key) throws IOException {
        PutObjectRequest objectRequest = getPutObjectRequest(key, file.getContentType());
        RequestBody rb = getFileRequestBody(file);
        s3Client.putObject(objectRequest, rb);
        return findUploadKeyUrl(key);
    }

    // 파일 업로드를 하기위한 PutObjectRequest를 반환합니다.
    // 주의 사항은 com.amazonaws Package가 아닌 software.amazon.awssdk를 사용해야 합니다.
    // key는 저장하고자 하는 파일의 이름(?)을 의미합니다.
    private PutObjectRequest getPutObjectRequest(String key, String contentType) {
        log.info("타입: {}", contentType);
        return PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key) // TODO invitationCode?
                .contentType(contentType)
                .build();
    }

    // MultipartFile을 업로드 하기위해 RequestBody.fromInputStream에 InputStream과 file의 Size를 넣어줍니다.
    private RequestBody getFileRequestBody(MultipartFile file) throws IOException {
        return RequestBody.fromInputStream(file.getInputStream(), file.getSize());
    }

    // S3Utilities를 통해 GetUrlRequest를 파라미터로 넣어 파라미터로 넘어온 key의 접근 경로를 URL로 반환받아 경로를 사용할 수 있다.
    private String findUploadKeyUrl(String key) {
        GetUrlRequest request = GetUrlRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        URL url = s3Client.utilities().getUrl(request);
        return url.toString();
    }

    public void deleteFile(String filename) {
        String key = prefix + "/" + filename;
        try {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();
            s3Client.deleteObject(deleteObjectRequest);
        } catch (S3Exception e) {
            throw new RuntimeException("Error deleting file from S3: " + e.getMessage());
        }
    }
}
