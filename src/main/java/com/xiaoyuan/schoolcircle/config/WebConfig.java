package com.xiaoyuan.schoolcircle.config;

import com.xiaoyuan.schoolcircle.interceptor.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private JwtInterceptor jwtInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                // 需要认证的接口（明确列出）
                .addPathPatterns(
                        "/api/product/add",
                        "/api/product/update",
                        "/api/product/delete/**",
                        "/api/product/user/**",     // 我的商品接口需要认证
                        "/api/order/**"             // 订单接口需要认证（新增）
                )
                // 公开接口（不需要 token）
                .excludePathPatterns(
                        "/api/user/login",
                        "/api/user/register",
                        "/api/product/list",
                        "/api/product/search",
                        "/api/product/{id}"        // 商品详情公开
                );
    }

    // 如果需要跨域支持，可以添加 CORS 配置（可选）
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://localhost:8080")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true);
    }

}