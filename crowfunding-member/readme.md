# 会员系统

## 1 环境搭建

### 1.1 项目架构图

![](../img/member-001.png)

### 1.2 crowfunding-member父工程

pom.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.member</groupId>
    <artifactId>crowfunding-member</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>pom</packaging>

    <properties>
        <maven.compiler.source>11</maven.compiler.source>
        <maven.compiler.target>11</maven.compiler.target>
    </properties>

    <dependencyManagement>
        <!--spring boot 2.2.2-->
        <dependencies>
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>2.2.2.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <!--spring cloud Hoxton.SR1-->
            <dependency>
                <groupId>org.springframework.cloud</groupId>
                <artifactId>spring-cloud-dependencies</artifactId>
                <version>Hoxton.SR1</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <!--spring cloud alibaba 2.1.0.RELEASE-->
            <dependency>
                <groupId>com.alibaba.cloud</groupId>
                <artifactId>spring-cloud-alibaba-dependencies</artifactId>
                <version>2.1.0.RELEASE</version>
                <type>pom</type>
                <scope>import</scope>
            </dependency>
            <dependency>
                <groupId>mysql</groupId>
                <artifactId>mysql-connector-java</artifactId>
                <version>8.0.22</version>
            </dependency>
            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>druid</artifactId>
                <version>1.2.4</version>
            </dependency>
            <dependency>
                <groupId>org.mybatis.spring.boot</groupId>
                <artifactId>mybatis-spring-boot-starter</artifactId>
                <version>2.1.4</version>
            </dependency>
            <dependency>
                <groupId>junit</groupId>
                <artifactId>junit</artifactId>
                <version>4.12</version>
            </dependency>
            <dependency>
                <groupId>log4j</groupId>
                <artifactId>log4j</artifactId>
                <version>1.2.17</version>
            </dependency>
            <dependency>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
                <version>1.18.16</version>
                <optional>true</optional>
            </dependency>
            <!-- util工具模块-->
            <dependency>
                <groupId>groupId</groupId>
                <artifactId>crowfunding-util</artifactId>
                <version>1.0-SNAPSHOT</version>
            </dependency>
        </dependencies>
    </dependencyManagement>

    <build>
        <plugins>
            <plugin>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-maven-plugin</artifactId>
                <configuration>
                    <fork>true</fork>
                    <addResources>true</addResources>
                </configuration>
            </plugin>
        </plugins>
    </build>

</project>
```

### 1.3 eureka-server7001模块

#### 依赖

```xml
<dependencies>
    <!--eureka-server-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-server</artifactId>
    </dependency>
    <!--boot web actuator-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <!-- devtools-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>
</dependencies>
```

#### 配置

```yml
server:
  port: 7001

eureka:
  instance:
    hostname: localhost #eureka服务端的实例名称
  client:
    #false表示不向注册中心注册自己。
    register-with-eureka: false
    #false表示自己端就是注册中心，我的职责就是维护服务实例，并不需要去检索服务
    fetch-registry: false
    service-url:
      # 集群
      # 设置与Eureka Server交互的地址查询服务和注册服务都需要依赖这个地址。
      #      defaultZone: http://eureka7002.com:7002/eureka
      # 单机
      defaultZone: http://${eureka.instance.hostname}:${server.port}/eureka
```

#### 主启动

```java
@EnableEurekaServer
@SpringBootApplication
public class EurekaMain7001 {
    public static void main(String[] args) {
        SpringApplication.run(EurekaMain7001.class,args);
    }
}
```

### 1.4 逆向工程

#### 创建数据库表

```sql
create table member(
	id INT(11) NOT NULL auto_increment,
	loginacct VARCHAR(255) NOT NULL,
	userpswd CHAR(200) NOT NULL,
	username VARCHAR(255),
	email VARCHAR(255),
	authstatus INT(4) COMMENT '实名认证状态0 - 未实名认证， 1 - 实名认证申
	请中， 2 - 已实名认证',
	usertype INT(4) COMMENT ' 0 - 个人， 1 - 企业',
	realname VARCHAR(255),
	cardnum VARCHAR(255),
	accttype INT(4) COMMENT '0 - 企业， 1 - 个体， 2 - 个人， 3 - 政府',
	PRIMARY KEY (id)
);
```

#### 逆向工程

```xml
<!DOCTYPE generatorConfiguration PUBLIC
        "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
        "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">
<generatorConfiguration>
    <context id="simple" targetRuntime="MyBatis3">
        <!--         nullCatalogMeansCurrent=true 解决生成的实体类与数据库表不一样的问题-->
        <jdbcConnection driverClass="com.mysql.cj.jdbc.Driver"
                        connectionURL="jdbc:mysql://localhost:3306/crowfunding?serverTimezone=UTC&amp;nullCatalogMeansCurrent=true"
                        userId="root"
                        password="bruce123" />

        <!--实体类-->
        <javaModelGenerator targetPackage="com.member.entities.po" targetProject="./crowfunding-member/member-entities/src/main/java"/>
        <!--映射文件-->
        <sqlMapGenerator targetPackage="/resources/mapper" targetProject="./crowfunding-member/service-provider8001/src/main"/>
        <!--接口类-->
        <javaClientGenerator type="XMLMAPPER" targetPackage="com.member.mapper" targetProject="./crowfunding-member/service-provider8001/src/main/java"/>

        <table tableName="member" />
    </context>
</generatorConfiguration>
```

```java
public class MemberGenerator {
    public static void main(String[] args) {
        try {
            List<String> warnings = new ArrayList<String>();
            boolean overwrite = true;
            File configFile = new File("./crowfunding-reverse/src/main/resources/generatorConfigMember.xml");
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

### 1.5 member-entities模块

#### 实体类细分

- PO(Persistent Object)持久化对象

  用途1：将数据封装到PO 对象存入数据库
  用途2：将数据库数据查询出来存入PO 对象
  所以PO 对象是和数据库表对应，一个数据库表对应一个PO 对象

- VO(View Object)视图对象

  用途1：接收浏览器发送过来的数据
  用途2：把数据发送给浏览器去显示

- DO(Data Object)数据对象

  用途1：从Redis 查询得到数据封装为DO 对象
  用途2：从ElasticSearch 查询得到数据封装为DO 对象
  用途3：从Solr 查询得到数据封装为DO 对象
  ……
  从中间件或其他第三方接口查询到的数据封装为DO 对象

- DTO(Data Transfer Object)数据传输对象

  用途1：从Consumer 发送数据到Provider
  用途2：Provider 返回数据给Consumer

#### 依赖

```xml
<dependencies>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>
</dependencies>
```

### 1.6 service-provider8001

#### 依赖

```xml
<dependencies>
    <!-- 整合Redis -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-redis</artifactId>
    </dependency>
    <!-- 整合Mybatis-->
    <dependency>
        <groupId>org.mybatis.spring.boot</groupId>
        <artifactId>mybatis-spring-boot-starter</artifactId>
    </dependency>
    <!-- MySQL 驱动-->
    <dependency>
        <groupId>mysql</groupId>
        <artifactId>mysql-connector-java</artifactId>
    </dependency>
    <!-- 数据库连接池-->
    <dependency>
        <groupId>com.alibaba</groupId>
        <artifactId>druid</artifactId>
    </dependency>
    <!-- SpringBoot 测试-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
    <!-- 对外暴露服务-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <!-- 作为客户端访问Eureka 注册中心-->
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
    <!-- lombok-->
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
    </dependency>
    <!-- 日志-->
    <dependency>
        <groupId>log4j</groupId>
        <artifactId>log4j</artifactId>
    </dependency>
    <!-- 单元测试-->
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
    </dependency>
    <!-- 实体类模块-->
    <dependency>
        <groupId>com.member</groupId>
        <artifactId>member-entities</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
    <!-- util工具模块-->
    <dependency>
        <groupId>groupId</groupId>
        <artifactId>crowfunding-util</artifactId>
    </dependency>
    <!-- devtool-->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>
</dependencies>
```

#### 配置

```yml
server:
  port: 8001
spring:
  application:
    name: service-provider
  datasource:
    type: com.alibaba.druid.pool.DruidDataSource
    driver-class-name:  com.mysql.cj.jdbc.Driver
    url: jdbc:mysql://localhost:3306/crowfunding?serverTimezone=UTC&nullCatalogMeansCurrent=true
    username: root
    password: bruce123
  redis:
    database: 0
    host: 127.0.0.1
    port: 6379
    jedis:
      pool:
        # 连接池最大连接数，负数表示没有限制
        max-active: 20
        # 连接池最大阻塞等待时间，负数表示没有限制
        max-wait: -1
        # 连接池最大空闲链接
        max-idle: 10
        # 连接池最小空闲链接
        min-idle: 0
    # 链接超时时间
    timeout: 1000

eureka:
  client:
    #表示是否将自己注册进EurekaServer默认为true。
    register-with-eureka: true
    #是否从EurekaServer抓取已有的注册信息，默认为true。单节点无所谓，集群必须设置为true才能配合ribbon使用负载均衡
    fetchRegistry: true
    service-url:
      defaultZone: http://localhost:7001/eureka
  instance:
    instance-id: provider8001
    prefer-ip-address: true

mybatis:
  mapperLocations: classpath:mapper/*Mapper.xml
  type-aliases-package: com.member.entities.po    # 所有Entity别名类所在包
```

#### 主启动类

```java
@SpringBootApplication
public class ServiceProviderMain8001 {
    public static void main(String[] args) {
        SpringApplication.run(ServiceProviderMain8001.class,args);
    }
}
```

#### 创建service、controller

...

### 1.7 authentication-consumer9001模块

#### 依赖

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-openfeign</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-thymeleaf</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-openfeign</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>groupId</groupId>
        <artifactId>crowfunding-util</artifactId>
    </dependency>
    <!-- 实体类模块-->
    <dependency>
        <groupId>com.member</groupId>
        <artifactId>member-entities</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```

#### 配置

```yml
server:
  port: 9001

spring:
  application:
    name: auth-consumer
  thymeleaf:
    prefix: classpath:/templates/
    suffix: .html

eureka:
  client:
    #表示是否将自己注册进EurekaServer默认为true。
    register-with-eureka: true
    #是否从EurekaServer抓取已有的注册信息，默认为true。单节点无所谓，集群必须设置为true才能配合ribbon使用负载均衡
    fetchRegistry: true
    service-url:
      defaultZone: http://localhost:7001/eureka
  instance:
    instance-id: consumer9001
    prefer-ip-address: true
```

#### 主启动类

```java
@EnableFeignClients
@SpringBootApplication
public class AuthenticationConsumerMain9001 {
    public static void main(String[] args) {
        SpringApplication.run(AuthenticationConsumerMain9001.class,args);
    }
}
```

#### MemberFeignService

```java
@FeignClient(value = "service-provider")
@Service
public interface MemberFeignService {
   
}
```

#### AuthenticationConsumerController

```java
@Controller
public class AuthenticationConsumerController {

    @Autowired
    private MemberFeignService memberFeignService;

    @RequestMapping("/")
    public String showIndexPage(){
        return "index";
    }
    
    ...
}
```

### 1.8 gateway-zuul80模块

#### 依赖

```xml
<dependencies>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-zuul</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
</dependencies>
```

#### 配置

```yml
server:
  port: 80

spring:
  application:
    name: gateway-zuul80

eureka:
  client:
    service-url:
      defaultZone: http://localhost:7001/eureka
  instance:
    instance-id: zuul
    prefer-ip-address: true

zuul:
#  ignored-services: "*"  # 忽略多个
  sensitive-headers: "*"  # 在Zuul 向其他微服务重定向时保持原本头信息（请求头、响应头）
  host:
    connect-timeout-millis: 3000
    socket-timeout-millis: 3000
  routes:
    consumer:
      service-id: auth-consumer
      path: /**
```

#### 主启动类

```java
@EnableZuulProxy
@SpringBootApplication
public class ZuulMain80 {
    public static void main(String[] args) {
        SpringApplication.run(ZuulMain80.class,args);
    }
}
```



