package com.shxy.w202350766.campusserviceplatform.service;

import org.springframework.web.multipart.MultipartFile;

public interface OssService {
    /**
     * 上传头像到OSS
     * @param file 头像文件
     * @return 文件在OSS中的URL
     */
    String uploadAvatar(MultipartFile file);
}
