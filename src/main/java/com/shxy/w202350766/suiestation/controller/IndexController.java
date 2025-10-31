package com.shxy.w202350766.suiestation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 首页控制器
 * @Author 吴汇明
 * @School 绥化学院
 * @CreateTime 2025-10-30 16:43:01
 */
@Controller
public class IndexController {

    /**
     * 首页
     */
    @GetMapping({"/", "/index"})
    public String index() {
        return "index";
    }

    /**
     * 登录页面
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 注册页面
     */
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    /**
     * 校园论坛
     */
    @GetMapping("/forum")
    public String forum() {
        return "forum";
    }

    /**
     * 二手市场
     */
    @GetMapping("/market")
    public String market() {
        return "market";
    }

    /**
     * 失物招领
     */
    @GetMapping("/lost-found")
    public String lostFound() {
        return "lost-found";
    }

    /**
     * 校园跑腿
     */
    @GetMapping("/errand")
    public String errand() {
        return "errand";
    }

    /**
     * 资源共享
     */
    @GetMapping("/resource")
    public String resource() {
        return "resource";
    }

    /**
     * 个人中心
     */
    @GetMapping("/user/profile")
    public String profile() {
        return "profile";
    }
}