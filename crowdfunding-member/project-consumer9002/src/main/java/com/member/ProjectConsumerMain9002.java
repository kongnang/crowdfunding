package com.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author qiu
 * @create 2022-04-13 11:02
 */
@SpringBootApplication
public class ProjectConsumerMain9002 {
    public static void main(String[] args) {
        SpringApplication.run(ProjectConsumerMain9002.class,args);
    }
}
