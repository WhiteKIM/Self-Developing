package whitekim.self_developing.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 모든 경로에 대해
                .allowedOrigins("*") // 모든 Origin 허용 (개발/테스트용)
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // 허용할 HTTP 메소드
                .allowedHeaders("*") // 모든 헤더 허용
                .allowCredentials(false) // 인증정보(쿠키 등) 허용 여부
                .maxAge(3600); // Preflight 캐싱 시간(초)

    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /api 경로와 구분해서 /script/** 를 static/script 매핑
        registry.addResourceHandler("/script/**")
                .addResourceLocations("classpath:/static/script/")
                .setCachePeriod(0);
    }
}
