package com.shxy.w202350766.suiestation.service;

import com.shxy.w202350766.suiestation.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.shxy.w202350766.suiestation.domain.dto.UserDTO;
import com.shxy.w202350766.suiestation.utils.Result;
import com.shxy.w202350766.suiestation.domain.vo.UserVO;
import org.springframework.web.multipart.MultipartFile;

/**
* @author 33046
* @description 针对表【user(用户表)】的数据库操作Service
* @createDate 2025-10-30 17:00:59
*/
public interface UserService extends IService<User> {

    Result<UserVO> register(UserDTO user, MultipartFile avatar);

    Result<UserVO> login(UserDTO user);

    Result<Void> logout(String token);
    
    /**
     * 根据用户ID获取用户信息
     * @param userId 用户ID
     * @return 用户信息
     */
    UserVO getUserById(Long userId);

    Result<UserVO> updateUser(UserDTO userDTO, Long userId);

    UserVO updateUserAvatar(MultipartFile file, Long userId);

    Result<UserVO> updateUserPassword(UserDTO userDTO, Long userId);
}