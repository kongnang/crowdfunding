package com.member;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

/**
 * @author qiu
 * @create 2022-03-06 19:19
 */
@EnableZuulProxy
@SpringBootApplication
public class ZuulMain80 {
    public static void main(String[] args) {
        SpringApplication.run(ZuulMain80.class,args);
    }
}
