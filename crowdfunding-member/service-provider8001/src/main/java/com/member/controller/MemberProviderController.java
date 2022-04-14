package com.member.controller;

import com.constant.CrowFundingConstant;
import com.member.entities.po.Member;
import com.member.service.MemberService;
import com.util.CrowFundingUtil;
import com.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

/**
 * @author qiu
 * @create 2022-03-05 21:30
 */
@RestController
public class MemberProviderController {

    @Autowired
    private MemberService memberService;

    /**
     * 插入用户
     * @param member
     * @return
     */
    @RequestMapping("/provider/member/add")
    public ResultEntity<String> insertSelective(@RequestBody Member member){
        try {
            int result = memberService.insertSelective(member);
            if(result > 0){
                return ResultEntity.successWithoutData();
            }
            return ResultEntity.fail(CrowFundingConstant.MESSAGE_INSERT_FAILED);
        } catch (Exception e) {
            if(e instanceof DuplicateKeyException){
                return ResultEntity.fail(e.getMessage());
            }
            return ResultEntity.fail(e.getMessage());
        }
    }

    /**
     *
     * @param loginAcct
     * @return
     */
    @RequestMapping("/provider/member/get/by/loginAcct/{loginAcct}")
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
    @RequestMapping("/provider/member/get/by/id/{id}")
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
