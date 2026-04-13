package com.xiaoyuan.schoolcircle.interceptor;

import com.xiaoyuan.schoolcircle.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class JwtInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 放行所有 OPTIONS 预检请求（安全，不携带业务数据）
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            return true;
        }

        // 可选的公开路径放行（登录、注册、商品列表、搜索）
        String path = request.getRequestURI();
        if (path.equals("/api/user/login") || path.equals("/api/user/register") ||
                path.equals("/api/product/list") || path.startsWith("/api/product/search")) {
            return true;
        }

        // 其他接口必须验证 token
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            response.setStatus(401);
            response.getWriter().write("未登录或 token 无效");
            return false;
        }
        String token = authHeader.substring(7);
        if (!jwtUtil.validateToken(token)) {
            response.setStatus(401);
            response.getWriter().write("token 已过期或无效");
            return false;
        }
        Integer userId = jwtUtil.getUserId(token);
        request.setAttribute("userId", userId);
        return true;
    }
}