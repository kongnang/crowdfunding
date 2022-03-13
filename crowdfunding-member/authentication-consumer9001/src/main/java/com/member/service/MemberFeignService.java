package com.member.service;

import com.member.entities.po.Member;
import com.util.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.concurrent.TimeUnit;

/**
 * @author qiu
 * @create 2022-03-06 20:37
 */
@FeignClient(value = "service-provider")
@Service
public interface MemberFeignService {

    //redis
    @RequestMapping("provider/redis/set/timeout")
    public ResultEntity<String> setKeyValueTimeout(@RequestParam("key") String key,
                                           @RequestParam("value") String value,
                                           @RequestParam("time") Long time,
                                           @RequestParam("timeUnit") TimeUnit timeunit);

    @RequestMapping("/provider/redis/delete/key")
    public ResultEntity<String> removeKey(@RequestParam("key") String key);

    @RequestMapping("/provider/redis/set/key")
    public ResultEntity<String> setKyeValue(@RequestParam("key") String key,
                                            @RequestParam("value") String value);

    @RequestMapping("/provider/redis/get/key")
    public ResultEntity<String> getValue(@RequestParam("key") String key);

    // mysql
    @RequestMapping("/provider/member/add")
    public ResultEntity<String> insertSelective(@RequestBody Member member);

    @RequestMapping("/provider/member/get/by/loginAcct/{loginAcct}")
    public ResultEntity<Member> getByLoginAcct(@RequestParam("loginAcct") String loginAcct);

    @RequestMapping("/provider/member/get/by/id/{id}")
    public ResultEntity<Member> getById(@PathVariable("id") Integer id);
}
