package com.shxy.w202350766.suiestation.domain.dto;
import lombok.Data;
@Data
public class UserDTO {
    private String avatar;
    private String currentPassword;
    private String newPassword;
    private String confirmPassword;
    private String nickname;
    private String password;
    private Integer gender;
    private String phone;
    private String username;
    private String rememberMe;
    private String bio;
}