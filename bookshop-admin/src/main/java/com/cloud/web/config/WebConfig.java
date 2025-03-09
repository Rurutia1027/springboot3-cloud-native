package com.cloud.web.config;

import com.cloud.web.interceptor.TimeInterceptor;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final TimeInterceptor timeInterceptor;

    // ✅ Constructor-based Injection (Best Practice)
    public WebConfig(TimeInterceptor timeInterceptor) {
        this.timeInterceptor = timeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(timeInterceptor)
                .addPathPatterns("/**") // Intercept all paths
                .excludePathPatterns("/static/**", "/css/**", "/js/**"); // Exclude static resources
    }

    @Bean
    public FilterRegistrationBean characterEncodingFilterRegister() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        CharacterEncodingFilter filter = new CharacterEncodingFilter("UTF-8");
        filter.setForceEncoding(true);
        registrationBean.setFilter(filter);

        List<String> urls = List.of("/*");
        registrationBean.setUrlPatterns(urls);

        return registrationBean;
    }
}