package com.henu.mybat;

import com.henu.mybat.service.SeckillService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class BootMybatisApplication        // extends SpringBootServletInitializer
{
    //重写configure方法可以使用war包，利用tomcat部署项目 extends SpringBootServletInitializer
    // @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(BootMybatisApplication.class);
    }

    public static void main(String[] args) {
       SpringApplicationBuilder builder=new SpringApplicationBuilder(BootMybatisApplication.class);
        builder.run(args);
        ConfigurableApplicationContext context= builder.context();
        SeckillService seckillService = context.getBean(SeckillService.class);
        System.out.println(seckillService);

        //SpringApplication.run(BootMybatisApplication.class, args);
    }
}
