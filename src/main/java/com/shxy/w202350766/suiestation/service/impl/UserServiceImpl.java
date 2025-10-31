package com.shxy.w202350766.suiestation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.shxy.w202350766.suiestation.domain.User;
import com.shxy.w202350766.suiestation.domain.dto.UserDTO;
import com.shxy.w202350766.suiestation.service.OssService;
import com.shxy.w202350766.suiestation.service.UserService;
import com.shxy.w202350766.suiestation.mapper.UserMapper;
import com.shxy.w202350766.suiestation.utils.JwtUtils;
import com.shxy.w202350766.suiestation.utils.Result;
import com.shxy.w202350766.suiestation.domain.vo.UserVO;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;

import static com.shxy.w202350766.suiestation.constant.ResponseStatusConstant.CONFLICT;
import static com.shxy.w202350766.suiestation.constant.ResponseStatusConstant.FAIL;

/**
 * @author 33046
 * @description 针对表【user(用户表)】的数据库操作Service实现
 * @createDate 2025-10-30 17:00:59
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
        implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private OssService ossService;

    /**
     * 用户注册
     * @param user
     * @param avatar
     * @return
     */
    public Result<UserVO> register(UserDTO user, MultipartFile avatar) {
        //1.校验用户名是否存在
        if (this.query().eq("username", user.getUsername()).one() != null) {
            return Result.fail(CONFLICT,"用户名已存在");
        }
        //2.校验密码是否一致
        if (!user.getPassword().equals(user.getConfirmPassword())) {
            return Result.fail(FAIL,"两次密码不一致");
        }
        //3.TODO 校验手机号是否存在 同一个手机号只能注册一个用户
        if (this.query().eq("phone", user.getPhone()).one() != null) {
            return Result.fail(CONFLICT,"手机号已存在");
        }
        User newUser = new User();
        BeanUtils.copyProperties(user, newUser);
        //4.头像是否上传
        if (avatar != null) {
            //TODO 头像上传
            //上传头像到OSS
            String avatarUrl = ossService.uploadAvatar(avatar);
            newUser.setAvatar(avatarUrl);
        }
        //5.密码加密
        String password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        newUser.setPassword(password);
        //6.注册用户
        userMapper.insert(newUser);
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(newUser, userVO);
        userVO.setUserId(newUser.getId());
        return Result.success(userVO);
    }

    /**
     * 用户登录
     * @param user
     * @return
     */
    public Result<UserVO> login(UserDTO user) {
        //1.校验用户是否存在
        User loginUser = this.query().eq("username", user.getUsername()).one();
        if (loginUser == null) {
            return Result.fail(FAIL,"用户名不存在");
        }
        //2.校验密码是否正确
        String password = DigestUtils.md5DigestAsHex(user.getPassword().getBytes());
        if (!loginUser.getPassword().equals(password)) {
            return Result.fail(FAIL,"密码错误");
        }
        //3.登录成功
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(loginUser, userVO);
        userVO.setUserId(loginUser.getId());
        
        //4.生成token（无论是否记住我都生成token）
        String token = JwtUtils.generateToken(loginUser.getId());
        // 添加Bearer前缀，方便前端处理
        userVO.setToken("Bearer " + token);
        
        //5.是否记住我
        //if (user.getRememberMe().equals("1")) {
            //TODO 记住我功能，可以设置更长的token有效期
            // 这里可以设置更长的有效期或使用其他机制
        //}
        return Result.success(userVO);
    }

    /**
     * 用户退出登录
     * @param token
     */
    public Result<Void> logout(String token) {
        //1.校验token是否为空
        if (token == null) {
            return Result.fail(FAIL,"token为空");
        }
        
        //2.校验token是否有效
        Long userId = JwtUtils.parseToken(token);
        if (userId == null) {
            return Result.fail(FAIL,"token无效");
        }
        
        //3.由于JWT是无状态的，我们无法在服务器端直接使token失效
        // 实际的退出登录逻辑是客户端清除本地存储的token
        // 这里可以添加日志记录或其他业务逻辑
        
        return Result.success(null);
    }

    /**
     * 根据用户ID获取用户信息
     * @param userId 用户ID
     * @return 用户信息
     */
    public UserVO getUserById(Long userId) {
        User user = this.getById(userId);
        if (user == null) {
            return null;
        }
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        userVO.setUserId(user.getId());
        return userVO;
    }

     /**
      * 更新用户信息
      * @param userDTO 用户信息DTO
      * @return Result<UserVO> 更新后的用户信息
      */
    public Result<UserVO> updateUser(UserDTO userDTO,Long userId) {
        //1.校验用户是否存在
        User updateUser = this.query().eq("id", userId).one();
        if (updateUser == null) {
            return Result.fail(FAIL,"用户不存在");
        }
        //2.更新用户信息
        BeanUtils.copyProperties(userDTO, updateUser);
        userMapper.updateById(updateUser);
        //3.返回更新后的用户信息
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(updateUser, userVO);
        userVO.setUserId(updateUser.getId());
        return Result.success(userVO);
    }

    /**
     * 更新用户头像
     * @param file 头像文件
     * @param userId 用户ID
     * @return 更新后的用户信息
     */
    public UserVO updateUserAvatar(MultipartFile file, Long userId) {
        //1.校验用户是否存在
        User updateUser = this.query().eq("id", userId).one();
        if (updateUser == null) {
            return null;
        }
        //2.上传头像到OSS
        String avatarUrl = ossService.uploadAvatar(file);
        //3.更新用户头像
        updateUser.setAvatar(avatarUrl);
        userMapper.updateById(updateUser);
        //4.返回更新后的用户信息
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(updateUser, userVO);
        userVO.setUserId(updateUser.getId());
        return userVO;
    }

    @Override
    public Result<UserVO> updateUserPassword(UserDTO userDTO, Long userId) {
        //1.校验用户是否存在
        User updateUser = this.query().eq("id", userId).one();
        if (updateUser == null) {
            return Result.fail(FAIL,"用户不存在");
        }
        //2.校验旧密码是否正确
        String oldPassword = DigestUtils.md5DigestAsHex(userDTO.getCurrentPassword().getBytes());
        if (!updateUser.getPassword().equals(oldPassword)) {
            return Result.fail(FAIL,"旧密码错误");
        }
        //3.更新密码
        updateUser.setPassword(DigestUtils.md5DigestAsHex(userDTO.getNewPassword().getBytes()));
        userMapper.updateById(updateUser);
        //5.返回更新后的用户信息
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(updateUser, userVO);
        userVO.setUserId(updateUser.getId());
        return Result.success(userVO);
    }
}