package com.withThymeleaf.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    /**
     * 视图控制器
     *
     * @param registry 视图控制器注册
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        // viewController => 视图控制器(请求资源路径)  viewName => 进行跳转的页面
        registry.addViewController("/login").setViewName("login");// 登录

        registry.addViewController("/register").setViewName("register");// 注册

        registry.addViewController("/addEmp").setViewName("addEmp");// 添加员工信息
    }

}