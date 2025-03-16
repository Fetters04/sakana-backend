package com.example.admin_template.service;

import com.aliyun.oss.OSS;
import com.aliyun.oss.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

/**
 * @author Fetters
 */
@Service
public class OssService {

    @Autowired
    private OSS ossClient;

    @Value("${aliyun.oss.bucketName}")
    private String bucketName;
    @Value("${aliyun.oss.endpoint}")
    private String endpoint;

    public String uploadFile(MultipartFile file) {
        try {
            // 生成唯一的文件名
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFileName = UUID.randomUUID() + fileExtension;

            // 创建 PutObjectRequest 对象
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, uniqueFileName, file.getInputStream());

            // 上传文件
            ossClient.putObject(putObjectRequest);

            // 返回文件的访问 URL
            return "https://" + bucketName + "." + endpoint + "/" + uniqueFileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}