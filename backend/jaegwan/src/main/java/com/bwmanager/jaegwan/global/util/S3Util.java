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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3Util {
    private final S3Client s3Client;

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;

    /**
     * 외부에서 쓰는 메서드
     * 파일, 접두어, id
     *
     * @param multipartFile
     * @param prefix
     * @param id
     * @return
     * @throws IOException
     */
    public String upload(MultipartFile multipartFile, String prefix, Long id) throws IOException {
        // 원래 파일 이름에서 확장자를 추출한다.
        String originalFilename = multipartFile.getOriginalFilename();

        String key = generateKey(prefix, id) + originalFilename;
        try {
            putS3(multipartFile, key);
            return findUploadKeyUrl(key);
        } catch (S3Exception e) {
            log.error("Error uploading file to S3: {}", e.getMessage());
            throw e;
        }
    }

    private String generateKey(String prefix, Long id) {
        // 새 파일 이름에 오늘 날짜를 추가한다.
        String date = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
        return prefix + "/" + id + "/" + date + "-";
    }

    /**
     * 실제 업로드 하는 메소드
     *
     * @param file
     * @param key
     * @return
     * @throws IOException
     */
    private String putS3(MultipartFile file, String key) throws IOException {
        PutObjectRequest objectRequest = getPutObjectRequest(key, file.getContentType());
        RequestBody rb = getFileRequestBody(file);
        s3Client.putObject(objectRequest, rb);
        return findUploadKeyUrl(key);
    }

    /**
     * 파일 업로드를 하기위한 PutObjectRequest를 반환합니다. (software.amazon.awssdk)
     * key는 저장하고자 하는 파일의 이름
     *
     * @param key
     * @param contentType
     * @return
     */
    private PutObjectRequest getPutObjectRequest(String key, String contentType) {
        return PutObjectRequest.builder()
                .bucket(bucketName)
                .key(key)
                .contentType(contentType)
                .build();
    }

    /**
     * MultipartFile을 업로드 하기위해 RequestBody.fromInputStream에 InputStream과 file의 Size를 넣어준다.
     *
     * @param file
     * @return
     * @throws IOException
     */
    private RequestBody getFileRequestBody(MultipartFile file) throws IOException {
        return RequestBody.fromInputStream(file.getInputStream(), file.getSize());
    }

    /**
     * S3Utilities를 통해 GetUrlRequest를 파라미터로 넣어 파라미터로 넘어온 key의 접근 경로를 URL로 반환받아 경로를 사용할 수 있다.
     *
     * @param key
     * @return
     */
    private String findUploadKeyUrl(String key) {
        GetUrlRequest request = GetUrlRequest.builder()
                .bucket(bucketName)
                .key(key)
                .build();

        URL url = s3Client.utilities().getUrl(request);
        return url.toString();
    }

    public void deleteFile(String prefix, Long id, String filename) {
        String key = generateKey(prefix, id) + "/" + filename;
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
