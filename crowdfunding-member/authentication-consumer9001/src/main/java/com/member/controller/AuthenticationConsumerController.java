package com.member.controller;

import com.member.service.MemberFeignService;
import com.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author qiu
 * @create 2022-03-06 15:51
 */
@Controller
public class AuthenticationConsumerController {

    @Autowired
    private MemberFeignService memberFeignService;

    @RequestMapping("/")
    public String showIndexPage(){
        return "index";
    }

    @ResponseBody
    @RequestMapping("/get/{id}")
    public ResultEntity getById(@PathVariable("id") Integer id){
        return memberFeignService.getById(id);
    }
}
