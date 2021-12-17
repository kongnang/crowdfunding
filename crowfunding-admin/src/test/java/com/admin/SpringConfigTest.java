package com.admin;

import com.admin.entity.Admin;
import com.admin.impl.AdminServiceImpl;
import com.admin.mapper.AdminMapper;
import com.admin.service.AdminService;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

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
