package com.walkingtalking.gunilda.auth.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.walkingtalking.gunilda.auth.interceptorr.TokenInterceptor;
import com.walkingtalking.gunilda.auth.provider.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {

    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TokenInterceptor(jwtProvider, objectMapper))
                .order(1)
                .addPathPatterns("/api/**")
                .excludePathPatterns("/api/auth/**");
    }
}
