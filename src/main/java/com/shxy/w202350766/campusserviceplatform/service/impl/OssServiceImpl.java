package com.shxy.w202350766.campusserviceplatform.service.impl;


import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.model.ObjectMetadata;
import com.qcloud.cos.model.PutObjectRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import com.shxy.w202350766.campusserviceplatform.service.OssService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {

    @Value("${tencent.cos.secret-id}")
    private String secretId;

    @Value("${tencent.cos.secret-key}")
    private String secretKey;

    @Value("${tencent.cos.region}")
    private String region;

    @Value("${tencent.cos.bucket-name}")
    private String bucketName;

    @Value("${tencent.cos.avatar-dir}")
    private String avatarDir;

    @Override
    // 上传头像到COS
    public String uploadAvatar(MultipartFile file) {
        COSClient cosClient = getCosClient(file);

        try {
            // 获取原始文件名
            String originalFilename = file.getOriginalFilename();
            // 获取文件扩展名
            String extension = "";
            if (originalFilename != null && originalFilename.contains(".")) {
                extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            }

            // 生成唯一文件名
            String fileName = avatarDir + UUID.randomUUID().toString().replaceAll("-", "") + extension;

            // 获取文件输入流
            InputStream inputStream = file.getInputStream();

            // 创建上传Object的Metadata
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            // 创建PutObjectRequest对象
            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, fileName, inputStream, objectMetadata);

            // 上传文件
            PutObjectResult putObjectResult = cosClient.putObject(putObjectRequest);

            // 返回文件访问URL
            String fileUrl = "https://" + bucketName + ".cos." + region + ".myqcloud.com/" + fileName;
            
            // 记录上传日志
            System.out.println("文件上传成功: " + fileUrl);
            
            return fileUrl;
        } catch (IOException e) {
            throw new RuntimeException("文件上传失败", e);
        } finally {
            // 关闭客户端
            if (cosClient != null) {
                cosClient.shutdown();
            }
        }
    }

    private COSClient getCosClient(MultipartFile file) {
        if (file == null || file.isEmpty()) {
            throw new RuntimeException("文件不能为空");
        }

        // 验证配置参数
        if (secretId == null || secretId.isEmpty() || secretKey == null || secretKey.isEmpty()) {
            throw new RuntimeException("腾讯云COS配置不完整，请检查secret-id和secret-key");
        }

        // 初始化用户身份信息
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        // 设置bucket的区域
        ClientConfig clientConfig = new ClientConfig(new Region(region));
        // 生成cos客户端
        COSClient cosClient = new COSClient(cred, clientConfig);
        return cosClient;
    }
}