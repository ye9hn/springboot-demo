package com.henu.mybat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
@SpringBootApplication
public class BootMybatisApplication        // extends SpringBootServletInitializer
{
    //重写configure方法可以使用war包，利用tomcat部署项目 extends SpringBootServletInitializer
    // @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BootMybatisApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(BootMybatisApplication.class, args);
    }
}
