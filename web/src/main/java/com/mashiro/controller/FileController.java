package com.mashiro.controller;

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
@RequestMapping("/system/file")
@RestController
public class FileController {
    @Resource
    private FileService fileService;

    /**
     * /**
     * 上传文件方法
     * 该方法负责接收上传的文件，并返回文件的URL
     *
     * @param file 通过@RequestParam注解接收的上传文件，直接从请求中获取
     * @param file
     * @return 返回一个Result对象，包含文件的URL
     * @return
     * @throws ServerException
     * @throws InsufficientDataException
     * @throws ErrorResponseException
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws InvalidResponseException
     * @throws XmlParserException
     * @throws InternalException
     */
    @Operation(summary = "上传文件")
    @PostMapping("upload")
    public Result<String> upload(@RequestParam MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        // 调用fileService的upload方法，上传文件并获取文件的URL
        String url = fileService.upload(file);
        // 返回一个成功的Result对象，包含文件的URL
        return Result.ok(url);
    }

    /**
     * 删除文件
     *
     * @param objectName
     * @return
     */
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
     *
     * @param objectName
     * @return
     */

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