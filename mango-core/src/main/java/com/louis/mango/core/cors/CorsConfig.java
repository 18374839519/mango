package com.louis.mango.core.cors;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * cors跨域工具类
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")  // 允许跨域访问的路径
                .allowedOrigins("*")  // 允许跨域访问的资源
                .allowedMethods("POST", "GET"/*, "PUT", "OPTIONS", "DELETE"*/)  // 允许的请求方法
                .maxAge(168000)  // 预检时间间隔
                .allowedHeaders("*") // 允许头部设置
                .allowCredentials(true);  // 是否发送cookies
    }
}
