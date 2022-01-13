package com.admin;

import com.admin.entity.Admin;
import com.admin.service.impl.AdminServiceImpl;
import com.admin.service.AdminService;
import com.github.pagehelper.PageInfo;
import org.apache.ibatis.annotations.Param;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;

import javax.sql.DataSource;
import javax.swing.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author qiu
 * @create 2021-12-16 16:36
 */
public class SpringConfigTest {

    /**
     * 获取当前系统时间测试
     *
     */
    @Test
    public void dateTimeTest(){
        Date date = new Date();
        System.out.println(date);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(simpleDateFormat.format(date));
    }

    /**
     * 日志打印测试
     *
     * 使用的是slf4j+logback
     */
    @Test
    public void logTest(){
        // 获取Logger对象
        Logger logger = LoggerFactory.getLogger(SpringConfigTest.class);

        logger.debug("debug level");
        logger.info("info level");
        logger.warn("warn level");
        logger.error("error level");
    }

    /**
     * adminService测试
     */
    @Test
    public void mapperTest(){
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-persist-mybatis.xml");
        // 需要在xml中配置bean
        AdminService adminService =(AdminService) context.getBean("adminService");

//        Admin admin = adminService.selectById(1);
//        System.out.println(admin);

//        PageInfo<Admin> pageInfo = adminService.selectByKeyWord("", 1, 5);
//        pageInfo.getList().forEach(System.out::println);
    }

    /**
     * dataSource测试
     * @throws SQLException
     */
    @Test
    public void dataSourceTest() throws SQLException {
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring-persist-mybatis.xml");
        DataSource dataSource =(DataSource) context.getBean("dataSource");

        Connection connection = dataSource.getConnection();
        System.out.println(connection);
    }

}
