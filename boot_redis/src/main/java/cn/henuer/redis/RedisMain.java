package cn.henuer.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

@SpringBootApplication
public class RedisMain {
    public static void main(String[] args) {
        SpringApplication.run(RedisMain.class,args);
        try{
            System.in.read();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
