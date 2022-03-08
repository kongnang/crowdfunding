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

    /**
     * 登录页面
     * @return
     */
    @RequestMapping("/login")
    public String loginPage(){
        return "member-login";
    }

    /**
     * 注册页面
     * @return
     */
    @RequestMapping("/register")
    public String registerPage(){
        return "member-register";
    }

    /**
     * 首页
     * @return
     */
    @RequestMapping("/")
    public String indexPage(){
        return "index";
    }

    @ResponseBody
    @RequestMapping("/get/{id}")
    public ResultEntity getById(@PathVariable("id") Integer id){
        return memberFeignService.getById(id);
    }
}
