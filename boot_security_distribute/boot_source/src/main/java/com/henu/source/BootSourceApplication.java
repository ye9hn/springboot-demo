package com.henu.source;

import com.henu.source.config.RsaKeyProperties;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@MapperScan("com.henu.source.mapper")
@EnableConfigurationProperties(RsaKeyProperties.class)
public class BootSourceApplication {
    public static void main(String[] args) {
        SpringApplication.run(BootSourceApplication.class,args);
    }
}
