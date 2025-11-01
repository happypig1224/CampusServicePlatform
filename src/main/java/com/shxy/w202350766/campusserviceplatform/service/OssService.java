package com.shxy.w202350766.campusserviceplatform.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    /**
     * 上传头像到OSS
     * @param file 头像文件
     * @return 文件在OSS中的URL
     */
    String uploadAvatar(MultipartFile file);
    
    /**
     * 上传图片到OSS
     * @param file 图片文件
     * @param dir 上传目录
     * @return 文件在OSS中的URL
     */
    String uploadImage(MultipartFile file, String dir);
}