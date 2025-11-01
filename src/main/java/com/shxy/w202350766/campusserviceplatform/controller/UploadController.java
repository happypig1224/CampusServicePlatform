package com.shxy.w202350766.campusserviceplatform.controller;

import com.shxy.w202350766.campusserviceplatform.service.OssService;
import com.shxy.w202350766.campusserviceplatform.utils.Result;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件上传控制器
 * @Author 吴汇明
 * @School 绥化学院
 * @CreateTime 2025-10-30 16:43:01
 */
@RestController
@RequestMapping("/api/upload")
public class UploadController {
    
    @Resource
    private OssService ossService;
    
    @Value("${tencent.cos.image-dir:images/}")
    private String imageDir;
    
    /**
     * 上传图片
     * @param file 图片文件
     * @return 上传结果，包含图片URL
     */
    @PostMapping("/image")
    public Result<Map<String, String>> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // 检查文件是否为空
            if (file.isEmpty()) {
                return Result.fail(400, "上传文件不能为空");
            }
            
            // 检查文件类型
            String contentType = file.getContentType();
            if (contentType == null || !contentType.startsWith("image/")) {
                return Result.fail(400, "只能上传图片文件");
            }
            
            // 检查文件大小（限制为5MB）
            if (file.getSize() > 5 * 1024 * 1024) {
                return Result.fail(400, "图片大小不能超过5MB");
            }
            
            // 上传文件到OSS
            String imageUrl = ossService.uploadImage(file, imageDir);
            
            // 返回图片URL
            Map<String, String> result = new HashMap<>();
            result.put("url", imageUrl);
            
            return Result.success(result);
        } catch (Exception e) {
            e.printStackTrace();
            return Result.fail(500, "图片上传失败：" + e.getMessage());
        }
    }
}