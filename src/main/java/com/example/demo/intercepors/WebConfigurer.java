package com.example.demo.intercepors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfigurer implements WebMvcConfigurer {

    @Autowired
    private LoginRecepor loginRecepor;

    /**
     * 静态资源的拦截处理
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    /**
     * 请求的拦截处理
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // addPathPatterns("/**") 表示拦截所有的请求
        registry.addInterceptor(loginRecepor).addPathPatterns("/**").
                excludePathPatterns("/","/wx/login","/knowledge/webDoChoice","/static/**");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/index.html");
        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);

    }
}
