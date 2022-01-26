package com.bodycodi.test;

// import com.catchsecu.test.common.AuthenticationInterceptor;
import com.bodycodi.test.common.AuthenticationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {   
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/messages")
                .excludePathPatterns("/users", "/login");
        
    }
}
