package com.shxy.w202350766.suiestation.controller;

import com.shxy.w202350766.suiestation.domain.dto.UserDTO;
import com.shxy.w202350766.suiestation.utils.JwtUtils;
import com.shxy.w202350766.suiestation.utils.Result;
import com.shxy.w202350766.suiestation.domain.vo.UserVO;
import com.shxy.w202350766.suiestation.service.UserService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 用户信息控制器
 * @Author 吴汇明
 * @School 绥化学院
 */
@RestController
@RequestMapping("/api/user")
public class UserController {
    
    @Resource
    private UserService userService;

    /**
     * 获取当前用户信息
     * @param request HTTP请求对象
     * @return Result<UserVO> 用户信息
     */
    @GetMapping("/info")
    public Result<UserVO> getUserInfo(HttpServletRequest request) {
       Long userId = (Long) request.getSession().getAttribute("userId");
        // 获取用户信息
        UserVO userVO = userService.getUserById(userId);
        if (userVO == null) {
            return Result.fail("用户不存在");
        }
        
        // 生成新的token并返回
        String token = JwtUtils.generateToken(userId);
        // 添加Bearer前缀，方便前端处理
        userVO.setToken("Bearer " + token);

        return Result.success(userVO);
    }

    @GetMapping("/profile")
    public Result<UserVO> getUserProfile(HttpServletRequest request) {
        Long userId = (Long) request.getSession().getAttribute("userId");
        if (userId == null) {
            return Result.fail("用户未登录");
        }
        UserVO userVO = userService.getUserById(userId);
        if (userVO == null) {
            return Result.fail("用户不存在");
        }
        return Result.success(userVO);
    }

    /**
     * 更新用户信息
     * @param userDTO 用户信息DTO
     * @return Result<UserVO> 更新后的用户信息
     */
    @PutMapping("/profile")
    public Result<UserVO> updateUserProfile(@RequestBody UserDTO userDTO, HttpServletRequest request) {
        // 首先尝试从session中获取userId（可能是String类型）
        Object sessionUserId = request.getSession().getAttribute("userId");
        Long userId = null;
        
        if (sessionUserId != null) {
            // 处理session中的userId，可能是String类型
            if (sessionUserId instanceof String) {
                userId = Long.parseLong((String) sessionUserId);
            } else if (sessionUserId instanceof Long) {
                userId = (Long) sessionUserId;
            }
        }
        
        // 如果session中没有，再尝试从request attribute中获取（由JwtInterceptor设置）
        if (userId == null) {
            userId = (Long) request.getAttribute("userId");
        }
        
        if (userId == null) {
            return Result.fail("用户未登录");
        }
        return userService.updateUser(userDTO, userId);
    }

    @PostMapping(value = "/avatar", consumes = "multipart/form-data")
    public Result<UserVO> updateUserAvatar(@RequestParam("avatar") MultipartFile file, HttpServletRequest request) {
        // 1.从session中获取userId
        Long userId = (Long) request.getSession().getAttribute("userId");
        if (userId == null) {
            return Result.fail("用户未登录");
        }
        // 2.检查文件是否为空
        if (file == null || file.isEmpty()) {
            return Result.fail("文件不能为空");
        }
        // 3.更新用户头像
        UserVO userVO = userService.updateUserAvatar(file, userId);
        if (userVO == null) {
            return Result.fail("更新头像失败");
        }
        return Result.success(userVO);

        }
    @PutMapping("/change-password")
    public Result<UserVO> updateUserPassword(@RequestBody UserDTO userDTO, HttpServletRequest request) {
        // 1.从session中获取userId
        Long userId = (Long) request.getSession().getAttribute("userId");
        if (userId == null) {
            return Result.fail("用户未登录");
        }
        // 2.检查密码是否为空
        if (userDTO.getCurrentPassword() == null || userDTO.getNewPassword() == null ) {
            return Result.fail("密码不能为空");
        }
        return userService.updateUserPassword(userDTO, userId);
    }
}