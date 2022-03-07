package com.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;


/**
 * @author qiu
 * @create 2022-03-06 15:48
 */
@EnableFeignClients
@SpringBootApplication
public class AuthenticationConsumerMain9001 {
    public static void main(String[] args) {
        SpringApplication.run(AuthenticationConsumerMain9001.class,args);
    }
}
