package com.henu.common;

import com.henu.common.config.CommonProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;


//@SpringBootApplication
//@EnableConfigurationProperties(CommonProperties.class)
public class BootCommonApplication {
    public static void main(String[] args) {
       //ApplicationContext context=
               SpringApplication.run(BootCommonApplication.class,args);

        //System.out.println(context.getBean(CommonProperties.class));
    }
}
