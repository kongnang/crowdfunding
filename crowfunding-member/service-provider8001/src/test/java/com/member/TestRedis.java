package com.member;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author qiu
 * @create 2022-03-06 13:35
 */
@SpringBootTest(classes = ServiceProviderMain8001.class)
@RunWith(SpringRunner.class)
public class TestRedis {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Test
    public void test(){
        ValueOperations<String, String> ops = redisTemplate.opsForValue();
        ops.set("apple","red");
    }
}
