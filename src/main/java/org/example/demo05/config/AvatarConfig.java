package org.example.demo05.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class AvatarConfig implements WebMvcConfigurer {
    @Value("${upload.location}")
    private String location;

    @Value("${upload.url}")
    private String url;

    //添加资源映射
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //配置映射
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:/" + location);
    }
}
