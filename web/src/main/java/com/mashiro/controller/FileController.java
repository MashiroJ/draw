package com.mashiro.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.mashiro.result.Result;
import com.mashiro.service.FileService;
import io.minio.errors.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Tag(name = "文件管理")
@SaCheckLogin
@RequestMapping("/system/file")
@RestController
public class FileController {
    @Resource
    private FileService fileService;

    /**
     * 上传文件方法
     */
    @Operation(summary = "上传文件")
    @PostMapping("upload")
    public Result<String> upload(@RequestPart(required = false) MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        String url = fileService.upload(file);
        return Result.ok(url);
    }

    /**
     * 通过URL上传文件
     */
    @Operation(summary = "通过URL上传文件")
    @PostMapping("/uploadByUrl")
    public Result<String> uploadByUrl(@RequestParam String fileUrl) {
        String url = fileService.uploadFromUrl(fileUrl);
        return Result.ok(url);
    }

    /**
     * 删除文件
     */
    @Operation(summary = "删除文件")
    @DeleteMapping("/delete")
    public String deleteImage(@RequestParam String objectName) {
        try {
            fileService.deleteImage(objectName);
            return "Image deleted successfully.";
        } catch (Exception e) {
            return "Error occurred while deleting the image: " + e.getMessage();
        }
    }

    /**
     * 获取文件流
     */
    @Operation(summary = "获取文件")
    @GetMapping("/view")
    public ResponseEntity<InputStreamResource> viewFile(@RequestParam String objectName) {
        try {
            InputStream fileStream = fileService.getFile(objectName);
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(new InputStreamResource(fileStream));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null);
        }
    }
}