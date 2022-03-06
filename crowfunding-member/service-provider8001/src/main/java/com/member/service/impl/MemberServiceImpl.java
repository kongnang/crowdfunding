package com.member.service.impl;

import com.member.entities.po.Member;
import com.member.entities.po.MemberExample;
import com.member.mapper.MemberMapper;
import com.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author qiu
 * @create 2022-03-05 21:26
 */
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    /**
     * 根据登录账号查询
     * @param loginAcct
     * @return
     */
    @Override
    public Member selectByLoginAcct(String loginAcct) {
        MemberExample example = new MemberExample();
        example.createCriteria().andLoginacctEqualTo(loginAcct);
        List<Member> members = memberMapper.selectByExample(example);
        return members.get(0);
    }

    /**
     * 插入
     * @param record
     * @return
     */
    @Override
    public int insert(Member record) {
        return memberMapper.insert(record);
    }

    /**
     * 根据id查找
     * @param id
     * @return
     */
    @Override
    public Member selectByPrimaryKey(Integer id) {
        return memberMapper.selectByPrimaryKey(id);
    }
}
