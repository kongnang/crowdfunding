package com.member.service;

import com.member.entities.po.Member;
import com.util.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author qiu
 * @create 2022-03-06 20:37
 */
@FeignClient(value = "service-provider")
@Service
public interface MemberFeignService {
    @RequestMapping("/provider/member/get/{id}")
    public ResultEntity<Member> getById(@PathVariable("id") Integer id);
}
