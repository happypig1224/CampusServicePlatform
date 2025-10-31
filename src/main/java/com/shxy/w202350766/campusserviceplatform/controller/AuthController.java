package com.shxy.w202350766.campusserviceplatform.controller;

import com.shxy.w202350766.campusserviceplatform.domain.dto.UserDTO;
import com.shxy.w202350766.campusserviceplatform.service.UserService;
import com.shxy.w202350766.campusserviceplatform.utils.Result;
import com.shxy.w202350766.campusserviceplatform.domain.vo.UserVO;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @Author 吴汇明
 * @School 绥化学院
 * @CreateTime 2025.10.30
 */
@Controller
@RequestMapping("/api/auth")
public class AuthController {
    @Resource
    private UserService userService;

    /**
     * 用户注册
     * @param user Register 用户注册信息
     * @param avatar 头像文件
     * @return Result<UserVO> 注册结果
     */
    @PostMapping("/register")
    @ResponseBody
    public Result<UserVO> register(@RequestBody UserDTO user, @RequestParam(value = "avatar",required = false) MultipartFile avatar) {
        return userService.register(user,avatar);
    }

     /**
     * 用户登录
     * @param user Login 用户登录信息
     * @param request HTTP请求对象
     * @return Result<UserVO> 登录结果
     */
    @PostMapping("/login")
    @ResponseBody
    public Result<UserVO> login(@RequestBody UserDTO user, HttpServletRequest request) {
        Result<UserVO> result = userService.login(user);
        
        // 如果登录成功，将用户信息存储到session中
        if (result.getCode() == 200 && result.getData() != null) {
            request.getSession().setAttribute("userId", result.getData().getUserId());
            request.getSession().setAttribute("username", result.getData().getUsername());
        }
        
        return result;
    }

    /**
     * 用户退出登录
     * @param request HTTP请求对象
     * @return Result<Void> 退出结果
     */
    @PostMapping("/logout")
    @ResponseBody
    public Result<Void> logout(HttpServletRequest request) {
        // 清除session中的用户信息
        request.getSession().removeAttribute("userId");
        request.getSession().removeAttribute("username");
        
        // 从请求头中获取token
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }
        return userService.logout(token);
    }

}