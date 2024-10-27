//package com.mashiro.config.cors;
//
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.config.annotation.CorsRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//@Configuration
// public class CorsConfig implements WebMvcConfigurer {
//
//     @Override
//     public void addCorsMappings(CorsRegistry registry) {
//         //支持所有接口
//         registry.addMapping("/**")
//                 //是否发送Cookie
//                .allowCredentials(true)
//                 //支持域
//                .allowedOriginPatterns("*")
//                .allowedMethods("GET", "POST", "PUT", "DELETE")
//                .allowedHeaders("*")
//                .exposedHeaders("*");
//    }
// }