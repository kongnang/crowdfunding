package com.member.service;

import com.member.entities.vo.ProjectVO;
import com.util.ResultEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author qiu
 * @create 2022-04-14 10:45
 */
@FeignClient(value = "service-provider")
@Service
public interface ProjectFeignService {

    @PostMapping("/provider/project/add")
    public ResultEntity<String> addProject(@RequestBody ProjectVO projectVO, @RequestParam("memberId") Integer memberId);
}
