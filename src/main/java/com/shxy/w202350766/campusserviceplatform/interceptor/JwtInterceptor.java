package com.shxy.w202350766.campusserviceplatform.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shxy.w202350766.campusserviceplatform.utils.JwtUtils;
import com.shxy.w202350766.campusserviceplatform.utils.Result;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * JWT拦截器，用于验证用户token
 * @Author 吴汇明
 * @School 绥化学院
 */
@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requestURI = request.getRequestURI();
        System.out.println("拦截器处理请求: " + requestURI);
        
        // 判断是否是页面请求（以/开头但不是/api/开头的请求）
        boolean isPageRequest = !requestURI.startsWith("/api/") && !requestURI.contains(".");
        System.out.println("是否是页面请求: " + isPageRequest);
        // 排除头像上传接口
        if (requestURI.equals("/api/user/avatar")) {
            System.out.println("头像上传接口，允许直接访问");
            return true;
        }
        // 对于页面请求，检查session中是否有用户信息
        if (isPageRequest) {
            Long userId = (Long)request.getSession().getAttribute("userId");
            System.out.println("从session中获取userId: " + userId);
            
            if (userId != null) {
                // session中有用户信息，允许访问
                System.out.println("从session验证通过，允许访问页面");
                return true;
            }
            
            // 对于登录页面和注册页面，允许直接访问
            if (requestURI.equals("/login") || requestURI.equals("/register") || requestURI.equals("/") || requestURI.equals("/index")) {
                System.out.println("访问公共页面，允许通过");
                return true;
            }
            
            // 其他页面需要登录，重定向到登录页面
            System.out.println("页面请求无session信息，重定向到登录页面");
            response.sendRedirect("/login");
            return false;
        }
        
        // 对于API请求，检查token
        System.out.println("处理API请求");
        
        // 从请求头中获取token
        String token = request.getHeader("Authorization");
        System.out.println("拦截器获取到的token: " + (token != null ? token.substring(0, Math.min(20, token.length())) + "..." : "null"));
        
        // 如果token为空
        if (token == null) {
            System.out.println("API请求无token，返回未授权错误");
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(new ObjectMapper().writeValueAsString(Result.error("未登录或token已过期")));
            return false;
        }
        
        // 如果token有Bearer前缀，去掉前缀
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
            System.out.println("移除Bearer前缀后的token: " + token.substring(0, Math.min(20, token.length())) + "...");
        } else {
            System.out.println("token没有Bearer前缀");
        }
        
        // 解析token
        Long userId = JwtUtils.parseToken(token);
        System.out.println("解析token得到的userId: " + userId);
        
        // 如果token无效
        if (userId == null) {
            System.out.println("API请求token无效，返回未授权错误");
            response.setContentType("application/json;charset=UTF-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write(new ObjectMapper().writeValueAsString(Result.error("用户未登录")));
            return false;
        }
        
        // 将用户ID存入请求属性，方便后续使用
        request.setAttribute("userId", userId);
        System.out.println("将userId存入请求属性: " + userId);
        
        return true;
    }
}