package com.henu.mongo.config;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;


/*
 *MongoDB配置类
 */
@Configuration
public class MongoDBConfig {
//    public @Bean
//    MongoClient mongoClient() {
//        return new MongoClient("192.168.0.103");
//    }
//
//    public @Bean
//    MongoTemplate mongoTemplate() {
//        return new MongoTemplate(mongoClient(), "mytest");
//    }

    /*
     * Factory bean that creates the com.mongodb.MongoClient instance
     */
//    public @Bean
//    MongoClientFactoryBean mongo() {
//        MongoClientFactoryBean mongo = new MongoClientFactoryBean();
//        mongo.setHost("192.168.0.103");
//        return mongo;
//    }
}
