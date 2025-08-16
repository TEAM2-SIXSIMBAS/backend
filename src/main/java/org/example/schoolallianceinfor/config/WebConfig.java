package org.example.schoolallianceinfor.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 실제 API 경로 전체 허용
                .allowedOrigins("http://localhost:5173") // 프론트 오리진
                .allowedMethods("GET","POST","PUT","DELETE","OPTIONS") // 프리플라이트 포함
                .allowedHeaders("*")
                .exposedHeaders("Location","Content-Disposition") // 필요시
                .allowCredentials(true)
                .maxAge(3600);
    }
}
