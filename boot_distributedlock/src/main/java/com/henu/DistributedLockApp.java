package com.henu;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DistributedLockApp {
    public static void main(String[] args) {
        SpringApplication.run(DistributedLockApp.class,args);
    }
}
