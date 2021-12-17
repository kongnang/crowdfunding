package com.admin;

import com.admin.entity.Admin;
import com.admin.service.impl.AdminServiceImpl;
import com.admin.service.AdminService;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author qiu
 * @create 2021-12-16 16:36
 */
public class SpringConfigTest {
    @Test
    public void test(){
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-mybatis.xml");
        AdminService adminService =(AdminServiceImpl) applicationContext.getBean("adminService");

        Admin admin = adminService.selectById(1);
        System.out.println(admin);
    }
}
