package io.bhimsur.apilimiter.configuration;

import io.bhimsur.apilimiter.filter.LimiterInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class BeanConfiguration implements WebMvcConfigurer {
    @Bean
    public LimiterInterceptor limiterInterceptor() {
        return new LimiterInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(limiterInterceptor());
    }
}
