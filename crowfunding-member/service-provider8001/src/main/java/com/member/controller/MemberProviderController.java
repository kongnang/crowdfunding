package com.member.controller;

import com.member.entities.po.Member;
import com.member.service.MemberService;
import com.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author qiu
 * @create 2022-03-05 21:30
 */
@RestController
public class MemberProviderController {

    @Autowired
    private MemberService memberService;

    /**
     *
     * @param loginAcct
     * @return
     */
    @RequestMapping("/provider/member/get/loginAcct")
    public ResultEntity<Member> getByLoginAcct(@RequestParam("loginAcct") String loginAcct){
        try {
            Member member = memberService.selectByLoginAcct(loginAcct);
            return ResultEntity.successWithData(member);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.fail(e.getMessage());
        }
    }

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping("/provider/member/get/{id}")
    public ResultEntity<Member> getById(@PathVariable("id") Integer id){
        try {
            Member member = memberService.selectByPrimaryKey(id);
            return ResultEntity.successWithData(member);
        } catch (Exception e) {
            e.printStackTrace();
            return ResultEntity.fail(e.getMessage());
        }
    }

}
