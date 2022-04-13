package com.member.controller;

import com.constant.CrowFundingConstant;
import com.member.config.ShortMessageProperties;
import com.member.entities.po.Member;
import com.member.entities.vo.MemberLoginVO;
import com.member.entities.vo.MemberVO;
import com.member.service.MemberFeignService;
import com.util.CrowFundingUtil;
import com.util.ResultEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author qiu
 * @create 2022-03-06 15:51
 */
@Controller
public class AuthenticationConsumerController {

    @Autowired
    private ShortMessageProperties shortMessageProperties;

    @Autowired
    private MemberFeignService memberFeignService;

    /**
     * 我的众筹页面
     * @return
     */
    @GetMapping("/mycrowdfunding")
    public String myCrowdfundingPage(){
        return "member-crowd";
    }

    /**
     * 退出登录
     * @param session
     * @return
     */
    @GetMapping("/logout")
    public String userLogout(HttpSession session){
        // 清除session数据
        session.invalidate();
        return "redirect:http://localhost/member/auth";
    }

    /**
     * 用户登录
     * @param loginacct
     * @param loginpswd
     * @param modelMap
     * @param session
     * @return
     */
    @RequestMapping("/do/login")
    public String userLoginPage(@RequestParam("loginacct")String loginacct,
                                @RequestParam("loginpswd") String loginpswd,
                                ModelMap modelMap,
                                HttpSession session){

        // 1.根据用户名获取用户信息
        ResultEntity<Member> memberResultEntity = memberFeignService.getByLoginAcct(loginacct);
        if(memberResultEntity.getOperationResult().equals(ResultEntity.FAILE)){
            // 账号不存在
            modelMap.addAttribute(CrowFundingConstant.MESSAGE_LOGIN_ACCOUNT_NOT_EXIST,memberResultEntity.getOperationMessage());
            return "redirect:http://localhost/member/auth/login";
        }
        Member member = memberResultEntity.getData();
        if(Objects.isNull(member)){
            modelMap.addAttribute(CrowFundingConstant.MESSAGE_LOGIN_ACCOUNT_NOT_EXIST,memberResultEntity.getOperationMessage());
            return "redirect:http://localhost/member/auth/login";
        }

        // 2.比较密码
        String encrypt = CrowFundingUtil.encrypt(loginpswd);
        if(!encrypt.equals(member.getUserpswd())){
            modelMap.addAttribute(CrowFundingConstant.MESSAGE_LOGIN_FAILED);
            return "redirect:http://localhost/member/auth/login";
        }

        // 3.存入session
        MemberLoginVO memberLoginVO = new MemberLoginVO(member.getId(),member.getUsername(),member.getEmail());
        session.setAttribute(CrowFundingConstant.MEMBER_LOGIN_NAME,memberLoginVO);

        return "redirect:http://localhost/member/auth/center";
    }

    /**
     * 用户页面
     * @return
     */
    @RequestMapping("/center")
    public String userCenterPage(){
        return "member-center";
    }

    /**
     * 用户注册
     * @param memberVO 这里不需要使用@RequestBody
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/add")
    public String addMember(MemberVO memberVO, ModelMap modelMap){

        String userPswd = memberVO.getUserpswd();
        String phoneNum = memberVO.getPhonenum();
        String code = memberVO.getCode();

        // 1.从Redis中读取验证码
        String key = CrowFundingConstant.REDIS_KEY_PREFIX+phoneNum;
        ResultEntity<String> vauleResultEntity = memberFeignService.getValue(key);

        // 2.判断Redis读取操作是否成功
        if(ResultEntity.FAILE.equals(vauleResultEntity.getOperationResult())){
            modelMap.addAttribute("message",vauleResultEntity.getOperationMessage());
            return "member-register";
        }

        // 3.判断读取的数据是否不为null
        String codeInRedis = vauleResultEntity.getData();
        if(codeInRedis == null){
            modelMap.addAttribute("message",CrowFundingConstant.MESSAGE_IDENTIFYING_CODE_INVALID);
            return "member-register";
        }

        // 4.判断表单中输入的验证码是否与Redis中一致
        if(!Objects.equals(code,codeInRedis)){
            modelMap.addAttribute("message",CrowFundingConstant.MESSAGE_IDENTIFYING_CODE_INVALID);
            return "member-register";
        }

        // 5.若一致则删除Redis中的验证码，并加密密码写入数据库
        memberFeignService.removeKey(key);
        String encryptedPswd = CrowFundingUtil.encrypt(userPswd);
        memberVO.setUserpswd(encryptedPswd);
        // 转换为PO
//        Member member = new Member(null,loginacct,encryptedPswd,username,email,null,null,null,null,null);
        Member member = new Member();
        BeanUtils.copyProperties(memberVO,member);

        ResultEntity<String> insertResultEntity = memberFeignService.insertSelective(member);
        if(ResultEntity.FAILE.equals(insertResultEntity.getOperationResult())){
            modelMap.addAttribute("message",CrowFundingConstant.MESSAGE_INSERT_FAILED);
            return "member-register";
        }

        return "redirect:http://localhost/member/auth/login";
    }

    /**
     * 获取验证码
     * @param phoneNum
     * @return
     */
    @ResponseBody
    @RequestMapping("/send/message.json")
    public ResultEntity<String> sendShortMessage(@RequestParam("phonenum") String phoneNum){

        String host = shortMessageProperties.getHost();
        String path = shortMessageProperties.getPath();
        String method = shortMessageProperties.getMethod();
        String appcode = shortMessageProperties.getAppcode();
        String template_id = shortMessageProperties.getTemplate_id();

        // 1发送验证码
        ResultEntity<String> sendMessageResultEntity = CrowFundingUtil.sendShortMessage(host, path, method, appcode, phoneNum, template_id);
        // 2判断是否发送成功
        if(ResultEntity.SUCCESS.equals(sendMessageResultEntity.getOperationResult())){
            // 3发送成功，将验证码存入Redis
            String code = sendMessageResultEntity.getData();
            ResultEntity<String> redisResultEntity = memberFeignService.setKeyValueTimeout(CrowFundingConstant.REDIS_KEY_PREFIX + phoneNum, code, 5L, TimeUnit.MINUTES);

            if(ResultEntity.SUCCESS.equals(redisResultEntity.getOperationResult())){
                return ResultEntity.successWithoutData();
            }else{
                return redisResultEntity;
            }//存入Redis失败
        }else{
            return sendMessageResultEntity;
        }// 发送短信失败
    }

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
