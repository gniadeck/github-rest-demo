package dev.gniadek.githubrestdemo.utils.configuration;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@AllArgsConstructor
public class WebMvcConfiguration implements WebMvcConfigurer {

    private HeaderVerifierInterceptor headerVerifierInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(headerVerifierInterceptor).addPathPatterns("/user/*/nonforkedrepos");
    }
}
