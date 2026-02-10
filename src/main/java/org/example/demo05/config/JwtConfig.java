package org.example.demo05.config;

import org.example.demo05.common.JwtInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class JwtConfig implements WebMvcConfigurer {
    private JwtInterceptor jwtInterceptor;

    @Autowired
    public void setJwtInterceptor(JwtInterceptor jwtInterceptor) {
        this.jwtInterceptor = jwtInterceptor;
    }

    //实现此方法，用于添加拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(jwtInterceptor)
                .addPathPatterns("/**")
                //不进行拦截的地址
                .excludePathPatterns("/api/users/login",
                        "/api/users/captcha",
                        "*.jpg", "*.png",
                        "*.gif", "*.css", "*.js");
    }
}
