package com.henu.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.util.List;

@Configuration
public class MyMvcConfig {
    // 所有的WebMvcConfigurerAdapter一起起作用,已过时可以使用以下WebMvcConfigurer
    //@Bean
//    public WebMvcConfigurerAdapter WebMvcConfigurerAdapter() {// springMVC的扩展类，在这里配置会覆盖默认自动注册方法
//        WebMvcConfigurerAdapter adapter = new WebMvcConfigurerAdapter() {
//            // 添加视图到controller
//            @Override
//            public void addViewControllers(ViewControllerRegistry registry) {
//
//            }
//            /**
//             * 视图配置
//             * @param registry
//             */
//            @Override
//            public void configureViewResolvers(ViewResolverRegistry registry) {
//                super.configureViewResolvers(registry);
//                registry.viewResolver(resourceViewResolver());
//                /* registry.jsp("/WEB-INF/jsp/",".jsp"); */
//            }
//            @Override
//            public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
//                configurer.enable();
//            }
//            // 添加拦截器
//            @Override
//            public void addInterceptors(InterceptorRegistry registry) {
//
//            }
//        };
//        return adapter;
//    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        WebMvcConfigurer webMvcConfigurer = new WebMvcConfigurer() {
            @Override
            public void configureViewResolvers(ViewResolverRegistry registry) {
                //注册视图控制器
                registry.viewResolver(resourceViewResolver());
            }

            @Override
            public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
                //注册json解析器,这里我们选择gson
                converters.add(new GsonHttpMessageConverter(){

                });
            }
        };
        return webMvcConfigurer;
    }

    public InternalResourceViewResolver resourceViewResolver() {
        InternalResourceViewResolver internalResourceViewResolver = new InternalResourceViewResolver();
        // 请求视图文件的前缀地址
        internalResourceViewResolver.setPrefix("classpath:/templates/");
        // 请求视图文件的后缀
        internalResourceViewResolver.setSuffix(".html");
        return internalResourceViewResolver;
    }
}
