package com.admin.controller;

import com.admin.entity.Admin;
import com.admin.service.AdminService;
import com.constant.CrowFundingConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

/**
 * @author qiu
 * @create 2021-12-27 9:47
 */
@Controller
public class AdminController{
    private Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Autowired
    private  AdminService adminService;

    /**
     * 管理员登录界面
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String adminLoginPage(){
        return "admin-login";
    }

    /**
     * 管理员提交账号密码，之后跳转到主页面
     * @param adminAcc
     * @param adminPwd
     * @param session
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public String adminLogin(@RequestParam("adminAcc")String adminAcc,
                             @RequestParam("adminPwd")String adminPwd,
                             HttpSession session){
        // 传入账号密码与数据库进行对比
        Admin admin = adminService.selectByAdminAccAndPwd(adminAcc, adminPwd);
        // 将管理员信息放入session域中
        session.setAttribute(CrowFundingConstant.ADAMIN_LOGIN_NAME,admin);

        // TODO 将sout换成logger.info
        System.out.println(admin);

        return "admin-main";
    }

    /**
     * 管理员主页面
     * @return
     */
    @RequestMapping(value = "/main")
    public String adminMainPage(){
        return "admin-main";
    }

    /**
     * 退出登录，重定向至管理员登录页面
     * @param session
     * @return
     */
    @RequestMapping(value = "/logout")
    public String adminLogOut(HttpSession session){
        // 强制session失效
        session.invalidate();

        return "redirect:/login";
    }
}
