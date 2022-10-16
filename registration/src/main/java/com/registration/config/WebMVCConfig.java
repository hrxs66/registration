package com.registration.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMVCConfig implements WebMvcConfigurer {


//    @Autowired
//    private LoginInterceptor loginInterceptor;


    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //跨域配置
        registry.addMapping("/**").allowedOrigins("http://localhost:8090","http://ab4e2brh4pv3.ngrok.xiaomiqiu123.top/");
    }

//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(loginInterceptor)
//                .addPathPatterns("/test")
//                .addPathPatterns("/comments/create/change")
//                .addPathPatterns("/articles/publish")
//                .addPathPatterns("/comments/delect");
//
//    }


}