package com.shxy.w202350766.campusserviceplatform.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @Author 吴汇明
 * @School 绥化学院
 * @CreateTime
 */
public interface UploadAvatarService {
    /**
     * 上传头像
     * @param avatar 头像文件
     * @return 头像URL
     */
    String uploadAvatar(MultipartFile avatar);
}
