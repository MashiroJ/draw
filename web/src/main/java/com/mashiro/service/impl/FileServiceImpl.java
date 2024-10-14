package com.mashiro.service.impl;

import com.mashiro.service.FileService;
import com.mashiro.utils.minio.MinioProperties;
import io.minio.*;
import io.minio.errors.*;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Resource
    private MinioClient minioClient;

    @Resource
    private MinioProperties minioProperties;


    /**
     * 创建桶策略配置
     * <p>
     * 该方法用于生成一个S3桶的策略配置字符串桶策略配置决定了谁可以访问桶中的对象
     *
     * @param backetName 桶的名称，用于策略配置中指定资源
     * @return 返回生成的桶策略配置字符串
     */
    private String createBucketPolicyConfig(String backetName) {
        // 格式化字符串，根据传入的桶名称生成对应的桶策略配置
        return """
                {
                  "Statement" : [ {
                    "Action" : "s3:GetObject",
                    "Effect" : "Allow",
                    "Principal" : "*",
                    "Resource" : "arn:aws:s3:::%s/*"
                  } ],
                  "Version" : "2012-10-17"
                }
                """.formatted(backetName);
    }


    /**
     * 上传文件到MinIO存储桶
     *
     * @param file 要上传的文件
     * @return 文件的访问URL
     * @throws ServerException           服务器异常
     * @throws InsufficientDataException 数据不足异常
     * @throws ErrorResponseException    错误响应异常
     * @throws IOException               输入输出异常
     * @throws NoSuchAlgorithmException  无此类算法异常
     * @throws InvalidKeyException       非法密钥异常
     * @throws InvalidResponseException  非法响应异常
     * @throws XmlParserException        XML解析异常
     * @throws InternalException         内部异常
     */
    @Override
    public String upload(MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        // 检查存储桶是否存在
        boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder()
                .bucket(minioProperties.getBucketName())
                .build());
        if (!bucketExists) {
            // 如果存储桶不存在，则创建新的存储桶并设置访问策略
            minioClient.makeBucket(MakeBucketArgs.builder()
                    .bucket(minioProperties.getBucketName())
                    .build());

            minioClient.setBucketPolicy(SetBucketPolicyArgs.builder()
                    .bucket(minioProperties.getBucketName())
                    .config(createBucketPolicyConfig(minioProperties.getBucketName()))
                    .build());
        }

        // 生成文件上传日期字符串
        String dateStr = new SimpleDateFormat("yyyyMMdd").format(new Date());
        // 生成唯一文件名，包含上传日期和文件原始名称
        String filename = dateStr + "/" + UUID.randomUUID() + "-" + file.getOriginalFilename();

        // 将文件上传到MinIO存储桶
        minioClient.putObject(PutObjectArgs.builder()
                .bucket(minioProperties.getBucketName())
                .stream(file.getInputStream(), file.getSize(), -1)
                .contentType(file.getContentType())
                .object(filename)
                .build());
        // 返回文件的访问URL
        return String.join("/", minioProperties.getEndpoint(), minioProperties.getBucketName(), filename);
    }

    @Override
    public void deleteImage(String objectName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        // 检查存储桶是否存在
        boolean bucketExists = minioClient.bucketExists(
                BucketExistsArgs.builder().bucket(minioProperties.getBucketName()).build()
        );
        if (!bucketExists) {
            throw new IllegalStateException("Bucket does not exist");
        }

        // 删除对象
        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket(minioProperties.getBucketName())
                        .object(objectName)
                        .build()
        );
    }

    @Override
    public InputStream getFile(String objectName) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        // 检查存储桶是否存在
        boolean bucketExists = minioClient.bucketExists(
                BucketExistsArgs.builder().bucket(minioProperties.getBucketName()).build()
        );
        if (!bucketExists) {
            throw new IllegalStateException("Bucket does not exist");
        }

        // 获取对象
        return minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(minioProperties.getBucketName())
                        .object(objectName)
                        .build()
        );
    }
}
