package edu.poly.assjava5banhang.config;

import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

public class WebMvcConFig  implements WebMvcConfigurer{

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/img/**") // Đường dẫn URL
                .addResourceLocations("file:D:/workspace_Java5/assjava5banhang/src/main/resources/static/img/"); // Thư mục thực tế
    }
}
