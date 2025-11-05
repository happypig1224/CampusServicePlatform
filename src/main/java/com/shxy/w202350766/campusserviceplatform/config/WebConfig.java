package com.shxy.w202350766.campusserviceplatform.config;

import com.shxy.w202350766.campusserviceplatform.interceptor.JwtInterceptor;
import jakarta.annotation.Resource;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Author 吴汇明
 * @School 绥化学院
 * @CreateTime 2025.10.30
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Resource
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**","/api/lost-found/items","/api/market/products/{id}/collect",
                        "/api/market/products",
                        "/api/lost-found/publishItems")
                .excludePathPatterns("/api/auth/login", "/api/auth/register", "/api/auth/logout")
                .excludePathPatterns("/static/**", "/css/**", "/js/**", "/images/**")
                .excludePathPatterns("/error", "/favicon.ico")
                .excludePathPatterns("/forum","/market","/lost-found","/errand","/resource")
                .excludePathPatterns("/", "/index", "/login", "/register")
                .excludePathPatterns("/api/forum/sections","/api/forum/posts","/api/forum/posts/{postId}","/api/forum/posts/hot" ,
                        "/api/forum/replies/latest","/api/forum/stats",
                        "/api/forum/post/{postId}")
                .excludePathPatterns("/api/lost-found/**")
                .excludePathPatterns("/api/market/categories","/api/market/products/hot","/api/market/products")
                .excludePathPatterns("/api/errand/categories","/api/errand/tasks/available"
                ,"/api/errand/runners","/api/errand/tasks/{taskId}");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
        registry.addViewController("/index").setViewName("index");
    }
}