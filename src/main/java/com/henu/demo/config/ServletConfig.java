package com.henu.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.Filter;


/**
 * 自定义注册一个Servlet，listener或者是filter
 */
//@Configuration
public class ServletConfig {
    @Autowired
    private DispatcherServlet dispatcherServlet;

    @Bean
    public ServletRegistrationBean myServlet() {
        ServletRegistrationBean bean = new ServletRegistrationBean(dispatcherServlet);
        //自定义映射
        bean.addUrlMappings("/");
        bean.setName("myServlet");
        return bean;
    }

    //注册一个filter
    public FilterRegistrationBean<Filter> myFilter() {
        return null;
    }
}
