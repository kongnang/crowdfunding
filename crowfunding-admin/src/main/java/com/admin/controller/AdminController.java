package com.admin.controller;

import com.admin.entity.Admin;
import com.admin.service.AdminService;
import com.constant.CrowFundingConstant;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

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
     * 实现修改功能
     * @param loginAcct
     * @param userName
     * @param email
     * @param session 通过session域获得当前修改的管理员对象
     * @param request 保存sql执行结果
     * @return
     */
    @RequestMapping(value = "/update" , method = RequestMethod.POST)
    public String updateAdmin(@RequestParam("loginAcct")String loginAcct,
                              @RequestParam("userName")String userName,
                              @RequestParam("email")String email,
                              HttpSession session,
                              HttpServletRequest request){

        Admin admin = (Admin) session.getAttribute("updateAdmin");
        admin.setLoginAcct(loginAcct);
        admin.setUserName(userName);
        admin.setEmail(email);

        Boolean res = adminService.updateAdminById(admin);
        if(res == true){
            request.setAttribute("res","修改成功");
        }else{
            request.setAttribute("res","修改失败");
        }

        return "redirect:/usermaintain";
    }

    /**
     * 跳转到管理员修改页面
     * @param session
     * @param id 由查询页面中的管理员信息获得
     * @return
     */
    @RequestMapping(value = "/update")
    public String updatePage(@RequestParam("id")Integer id,
                             HttpSession session){
        // 查询指定id的管理员信息，并放入请求域中以便显示在表单中
        Admin admin = adminService.selectById(id);
        session.setAttribute("updateAdmin",admin);

        return "admin-update";
    }

    /**
     * 根据管理员id删除指定管理员
     * @param request
     * @param id 由查询页面中的管理员信息获得
     * @param pageNum
     * @return 重定向至查询页面
     *
     * 最好做逻辑删除或者验证是否删除当前正在登录的账户，因为管理员有可能把自己的账号删除
     */
    @RequestMapping(value = "/delete/{id}/{pageNum}")
    public String deleteAdmin(HttpServletRequest request,
                              @PathVariable("id")Integer id,
                              @PathVariable("pageNum")Integer pageNum){

        Boolean res = adminService.deleteAdminById(id);

        if(res == true){
            request.setAttribute("res","删除成功！");
        }else{
            request.setAttribute("res","删除失败！");
        }

        return "redirect:/usermaintain";
    }

    /**
     * 添加管理员
     * @param request 保存sql执行结果
     * @param loginAcct
     * @param userName
     * @param email
     * @return
     */
    @RequestMapping(value = "/add" ,method = RequestMethod.POST)
    public String addAdmin(HttpServletRequest request,
                           @RequestParam("loginAcct")String loginAcct,
                           @RequestParam("userName")String userName,
                           @RequestParam("email")String email){

        Date date = new Date();
        Admin admin =new Admin(null,loginAcct,"123",userName,email,date);

        // 将保存的对象存入数据库
        Boolean res = adminService.insertAdmin(admin);

        // 将操作结果放入域中
        if(res == true){
            request.setAttribute("res","添加成功！");
        }else{
            request.setAttribute("res","添加失败！");
        }

        return "admin-add";
    }

    /**
     * 跳转至添加管理员页面
     * @return
     */
    @RequestMapping(value = "/add")
    public String addAdminPage(){
        return "admin-add";
    }

    /**
     * 通过关键字查询所有管理员，并分页，默认查询所有管理员
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/usermaintain")
    public String adminInfoResult(@RequestParam(value ="keyword",defaultValue = "")String keyword,
                                 @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                                 @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize,
                                 ModelMap modelMap){

        // 查询数据
        PageInfo<Admin> adminpageInfo = adminService.selectByKeyWord(keyword, pageNum, pageSize);

        // 将查询结果存入ModelMap
        modelMap.addAttribute(CrowFundingConstant.USER_MAINTAIN_PAGE,adminpageInfo);

        return "admin-maintain";
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

    /**
     * 管理员主页面
     * @return
     */
    @RequestMapping(value = "/main")
    public String adminMainPage(){
        return "admin-main";
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
//        System.out.println(admin);

        return "redirect:/main";
    }

    /**
     * 管理员登录界面
     * @return
     */
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    public String adminLoginPage(){
        return "admin-login";
    }
}
