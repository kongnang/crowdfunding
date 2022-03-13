package com.member.controller;

import com.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author qiu
 * @create 2022-03-06 14:29
 */
@RestController
public class RedisProviderController {

    /**
     * 指定RedisTemplate的K,V泛型，防止序列化K,V出现\xac\xed\x00\x05t\x00 的情况
     */
    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    /**
     *
     * @param key
     * @param value
     * @param time
     * @param timeunit
     * @return
     */
    @RequestMapping("provider/redis/set/timeout")
    public ResultEntity<String> setKeyValueTimeout(@RequestParam("key") String key,
                                           @RequestParam("value") String value,
                                           @RequestParam("time") Long time,
                                           @RequestParam("timeUnit")TimeUnit timeunit){
        try {
            ValueOperations<String,String> ops = redisTemplate.opsForValue();
            ops.set(key,value,time,timeunit);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.fail(e.getMessage());
        }
    }

    /**
     *
     * @param key
     * @return
     */
    @RequestMapping("/provider/redis/delete/key")
    public ResultEntity<String> removeKey(@RequestParam("key") String key){
        try {
            redisTemplate.delete(key);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.fail(e.getMessage());
        }
    }

    /**
     *
     * @param key
     * @param value
     * @return
     */
    @RequestMapping("/provider/redis/set/key")
    public ResultEntity<String> setKyeValue(@RequestParam("key") String key,
                                            @RequestParam("value") String value){
        try {
            ValueOperations<String,String> ops = redisTemplate.opsForValue();
            ops.set(key,value);
            return ResultEntity.successWithoutData();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.fail(e.getMessage());
        }
    }

    /**
     *
     * @param key
     * @return
     */
    @RequestMapping("/provider/redis/get/key")
    public ResultEntity<String> getValue(@RequestParam("key") String key){
        try {
            ValueOperations<String,String> ops = redisTemplate.opsForValue();
            String value = ops.get(key);
            return ResultEntity.successWithData(value);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.fail(e.getMessage());
        }
    }
}
