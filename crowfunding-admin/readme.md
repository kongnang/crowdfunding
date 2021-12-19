# 管理员系统

## 1 环境搭建

### 1.1  环境搭建总体目标

待写

### 1.2 创建数据库和数据库表

```sql	
DROP TABLE IF EXISTS admin;

CREATE TABLE admin(
	id int PRIMARY KEY auto_increment COMMENT '主键',
	login_acct VARCHAR(255) NOT NULL COMMENT '登录账号',
	user_pswd VARCHAR(255) NOT NULL COMMENT '登录密码',
	user_name VARCHAR(255) NOT NULL COMMENT '昵称',
	email VARCHAR(255) NOT NULL COMMENT '邮件地址',
	create_time date COMMENT '创建时间'
);
```

### 1.3 MyBatis 逆向工程

#### 1.3.1 依赖引入

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>reverse</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>jar</packaging>
    <properties>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
    </properties>

    <dependencies>
        <!-- https://mvnrepository.com/artifact/org.mybatis.generator/mybatis-generator-core -->
        <dependency>
            <groupId>org.mybatis.generator</groupId>
            <artifactId>mybatis-generator-core</artifactId>
            <version>1.3.7</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>8.0.22</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/com.alibaba/druid -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.2.6</version>
        </dependency>

    </dependencies>

</project>
```

#### 1.3.2 generatorConfig.xml

```xml
<!DOCTYPE generatorConfiguration PUBLIC
        "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
<generatorConfiguration>
    <context id="simple" targetRuntime="MyBatis3Simple">
        <jdbcConnection driverClass="com.mysql.cj.jdbc.Driver"
                        connectionURL="jdbc:mysql://localhost:3306/exercise?serverTimezone=UTC"
                        userId="root"
                        password="bruce123"/>

        <!--实体类-->
        <javaModelGenerator targetPackage="com.admin.entity" targetProject="./crowfunding-admin/src/main/java"/>
        <!--映射文件-->
        <sqlMapGenerator targetPackage="/resources" targetProject="./crowfunding-admin/src/main"/>
        <!--接口类-->
        <javaClientGenerator type="XMLMAPPER" targetPackage="com.admin.mapper" targetProject="./crowfunding-admin/src/main/java"/>

        <table tableName="admin" />
    </context>
</generatorConfiguration>
```

#### 1.3.3 生成器

```java
public class Generator {
    public static void main(String[] args) {
        try {
            List<String> warnings = new ArrayList<String>();
            boolean overwrite = true;
            File configFile = new File("./crowfunding-common/reverse/src/main/resources/generatorConfig.xml");
            ConfigurationParser cp = new ConfigurationParser(warnings);
            Configuration config = cp.parseConfiguration(configFile);
            DefaultShellCallback callback = new DefaultShellCallback(overwrite);
            MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
            myBatisGenerator.generate(null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
```

### 1.4 依赖引入

[pom.xml](./pom.xml)

### 1.5 Spring整合Mybatis

#### 1.5.1 配置数据库

```properties
jdbc.driver=com.mysql.cj.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/exercise?serverTimezone=UTC
jdbc.user=root
jdbc.password=bruce123
```

#### 1.5.2 配置spring-batis.xml

- 注册数据源`DataSource`
- 注册`SqlSessionFactoryBean`
- 注册`MapperScannerConfigurer`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/context
       http://www.springframework.org/schema/context/spring-context.xsd">

    <!--扫描IOC组件-->
    <context:component-scan base-package="com.admin"/>

    <!--配置数据源-->
    <context:property-placeholder location="classpath:jdbc.properties"/>
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource" destroy-method="close">
        <property name="driverClassName" value="${jdbc.driver}"/>
        <property name="url" value="${jdbc.url}"/>
        <property name="username" value="${jdbc.user}"/>
        <property name="password" value="${jdbc.password}"/>
    </bean>

    <!--配置SqlSessionFactoryBean-->
    <bean id="sqlSessionFactoryBean" class="org.mybatis.spring.SqlSessionFactoryBean">
        <!--装配数据源-->
        <property name="dataSource" ref="dataSource"/>
        <!--指定映射文件位置-->
        <property name="mapperLocations" value="classpath:mapper/*.xml"/>
    </bean>

    <!--配置MapperScannerConfigurer-->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <!--指定接口类所在包-->
        <property name="basePackage" value="com.admin.mapper"/>
    </bean>

    <bean id="adminService" class="com.admin.service.impl.AdminServiceImpl"/>
</beans>
```

#### 1.5.3 整合结果测试

创建`AdminService`接口和`AdminServiceImpl`实现类

在`AdminServiceImpl`中做CRUD的具体实现

```java
package com.admin.mapper;

public interface AdminMapper {
    Admin selectById(Integer id);
}
```

```java
package com.admin.service;

public interface AdminService {
    Admin selectById(Integer id);
}
```

```java
package com.admin.service.impl;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;

    @Override
    public Admin selectById(Integer id) {
        Admin admin = adminMapper.selectById(id);
        return admin;
    }
}
```

![](../img/admin-001.png)

测试：

```java
@Test
public void test(){
    //加载配置文件中的bean加载到容器中
    ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring-mybatis.xml");
    AdminService adminService =(AdminServiceImpl) applicationContext.getBean("adminService");

    Admin admin = adminService.selectById(1);
    System.out.println(admin);
}
```

### 1.6 日志系统

#### 1.6.1 日志门面与实现

- 门面
  - JCL(Jakarta Commons Logging)
  - SLF4J(Simple Logging Facade for Java)

- 实现
  - log4j
  - JUL(java.util.logging)
  - log4j2
  - logback

>SLF4J,log4j,logback是同一作者
>
>log4j2是Apache收购log4j后进行全面重构，内部实现和log4j完全不同

#### 1.6.2 SLF4J日志系统整合

SLF4J是日志门面，只提供了API没有实现，需要其它日志来实现

![](../img/admin-003.png)

SLF4J和logback-classic/log4j/jul的整合

![](../img/admin-004.png)

这里我们使用SLF4J和logback-classic的整合。

需要注意的是，Spring自带**commons-logging**包，需要**排除**该jar包后引入引入日志框架中间转换包。

![](../img/admin-002.png)

```xml
<!--日志-->
    <!-- https://mvnrepository.com/artifact/org.slf4j/slf4j-api -->
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>1.7.32</version>
    </dependency>
    <!-- https://mvnrepository.com/artifact/ch.qos.logback/logback-classic -->
    <dependency>
        <groupId>ch.qos.logback</groupId>
        <artifactId>logback-classic</artifactId>
        <version>1.2.7</version>
        <scope>test</scope>
    </dependency>
    <!--其它日志框架中间转换包-->
    <!-- https://mvnrepository.com/artifact/org.slf4j/jcl-over-slf4j -->
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>jcl-over-slf4j</artifactId>
        <version>1.7.32</version>
    </dependency>

<!--
排除spring中自带的commons-logging
可能不止这个依赖需要排除，其它spring依赖可能也需要
-->
	<dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-orm</artifactId>
            <version>5.3.13</version>
            <exclusions>
                <exclusion>
                    <groupId>commons-logging</groupId>
                    <artifactId>commons-logging</artifactId>
                </exclusion>
            </exclusions>
    </dependency>
```

#### 1.6.3 logback配置(简单配置)

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration debug="true">
    <!-- 指定日志输出的位置-->
    <appender name="STDOUT"
              class="ch.qos.logback.core.ConsoleAppender">
        <encoder>
            <!-- 日志输出的格式-->
            <!-- 按照顺序分别是：时间、日志级别、线程名称、打印日志的类、日志主体
            内容、换行-->
            <pattern>[%d{HH:mm:ss.SSS}] [%-5level] [%thread] [%logger]
                [%msg]%n</pattern>
        </encoder>
    </appender>
    <!-- 设置全局日志级别。日志级别按顺序分别是：DEBUG、INFO、WARN、ERROR -->
    <!-- 指定任何一个日志级别都只打印当前级别和后面级别的日志。-->
    <root level="INFO">
        <!-- 指定打印日志的appender，这里通过“STDOUT”引用了前面配置的appender -->
        <appender-ref ref="STDOUT" />
    </root>
    <!-- 根据特殊需求指定局部日志级别-->
    <logger name="com.admin.SpringConfigTest" level="DEBUG"/>
</configuration>
```

#### 1.6.4 测试

```java
@Test
public void logTest(){
    // 获取Logger对象
    Logger logger = LoggerFactory.getLogger(SpringConfigTest.class);

    logger.debug("debug level");
    logger.info("info level");
    logger.warn("warn level");
    logger.error("error level");
}
```

### 1.7 声明式事务

>事务是数据库操作最基本单元，逻辑上一组操作，要么都成功，如果有一个失败所有操作都失败。
>
>Spring中声明式事务管理有两种：基于xml和基于注解。
>
>该项目中使用的是基于xml方式

#### 1.7.1 编程式事务

```java
try {
    // 核心操作前：开启事务（关闭自动提交）
    // 对应AOP 的前置通知
    connection.setAutoCommit(false);
    // 核心操作
    adminService.updateXxx(xxx, xxx);
    // 核心操作成功：提交事务
    // 对应AOP 的返回通知
    connection.commit();
    }catch(Exception e){
    // 核心操作失败：回滚事务
    // 对应AOP 的异常通知
    connection.rollBack();
    }finally{
    // 不论成功还是失败，核心操作终归是结束了
    // 核心操作不管是怎么结束的，都需要释放数据库连接
    // 对应AOP 的后置通知
    if(connection != null){
    	connection.close();
    }
}
```

编程式事务每次都需要开启事务，关闭事务等操作，会造成代码臃肿，所以使用声明式事务来简化。

#### 1.7.2 基于xml的声明式事务管理

- 思路：

![](../img/admin-005.jpg)

- 引入AOP依赖

  ```XML
  <!-- https://mvnrepository.com/artifact/org.aspectj/aspectjweaver -->
  <dependency>
      <groupId>org.aspectj</groupId>
      <artifactId>aspectjweaver</artifactId>
      <version>1.9.6</version>
      <scope>runtime</scope>
  </dependency>
  <!-- https://mvnrepository.com/artifact/cglib/cglib -->
  <!--没有接口时使用CGLIB动态代理-->
  <dependency>
      <groupId>cglib</groupId>
      <artifactId>cglib</artifactId>
      <version>3.3.0</version>
  </dependency>
  ```

- 创建spring-tx.xml配置文件

  ```xml
  <!--配置事务管理器-->
  <bean id="dataSourceTransactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
      <property name="dataSource" ref="dataSource"/>
  </bean>
  
  <!--配置aop-->
  <aop:config>
      <!--配置切入点表达式-->
      <!--public Admin com.admin.service.impl.AdminServiceImpl(Integer id)-->
      <aop:pointcut id="pointCut" expression="execution(* * ..*service.impl.* (..))"/>
  
      <!--将事务通知和切入点表达式关联在一起-->
      <aop:advisor advice-ref="advisor" pointcut-ref="pointCut"/>
  </aop:config>
  
  <!--配置事务通知-->
  <!--transaction-manager 属性用于引用事务管理器-->
  <tx:advice id="advisor" transaction-manager="dataSourceTransactionManager">
      <tx:attributes>
          <!--name 属性指定当前要配置的事务方法的方法名
              一般将查询方法设置为只读，便于数据库根据只读属性进行相关性能优化-->
          <tx:method name="get*" read-only="true"/>
          <tx:method name="select*" read-only="true"/>
          <tx:method name="query*" read-only="true"/>
          <tx:method name="find*" read-only="true"/>
          <tx:method name="count*" read-only="true"/>
  
          <!--增删改查方法-->
          <!-- propagation 属性配置事务方法的传播行为
              默认值：REQUIRED 表示：当前方法必须运行在事务中，如果没有事务，则开
              启事务，在自己的事务中运行。如果已经有了已开启的事务，则在当前事务中运行。有可能
              和其他方法共用同一个事务。
              建议值：REQUIRES_NEW 表示：当前方法必须运行在事务中，如果没有事务，
              则开启事务，在自己的事务中运行。和REQUIRED 的区别是就算现在已经有了已开启的事务，
              也一定要开启自己的事务，避免和其他方法共用同一个事务。-->
          <!-- rollback-for 属性配置回滚的异常
               默认值：运行时异常
               建议值：编译时异常+运行时异常-->
          <tx:method name="insert*" propagation="REQUIRES_NEW" rollback-for="java.lang.Exception"/>
          <tx:method name="update*" propagation="REQUIRES_NEW" rollback-for="java.lang.Exception"/>
          <tx:method name="delete*" propagation="REQUIRES_NEW" rollback-for="java.lang.Exception"/>
      </tx:attributes>
  </tx:advice>
  ```

#### 1.7.3 基于注解的声明式事务管理

- 在配置文件中开启事务注解

  ```xml
  <!--开启事务注解--> 
  <tx:annotation-driven transaction-manager="transactionManager"/>
  ```

- 使用注解

  - @Transactional，这个注解添加到类上面，也可以添加方法上面
  - 如果把这个注解添加类上面，这个类里面所有的方法都添加事务
  - 如果把这个注解添加方法上面，为这个方法添加事务

  ```java
  @Service
  @Transactional
  public class AdminServiceImpl {}
  ```

  
