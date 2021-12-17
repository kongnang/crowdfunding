package com.admin;

import com.admin.entity.Admin;
import com.admin.service.impl.AdminServiceImpl;
import com.admin.service.AdminService;
import org.junit.jupiter.api.Test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author qiu
 * @create 2021-12-16 16:36
 */
public class SpringConfigTest {

    /**
     * IOC容器注入测试
     */
    @Test
    public void mapperTest(){

        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-mybatis.xml");
        AdminService adminService =(AdminServiceImpl) applicationContext.getBean("adminService");

        Admin admin = adminService.selectById(1);

        //System.out.println(admin);
        //实际开发中不适用system.out，其本质是IO操作，对性能影响较大
        //使用日志来打印
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
}
