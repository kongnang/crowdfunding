package com.member.service.impl;

import com.member.entities.po.Member;
import com.member.entities.po.MemberExample;
import com.member.mapper.MemberMapper;
import com.member.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * @author qiu
 * @create 2022-03-05 21:26
 */
@Service
@Transactional(readOnly = true) // 针对查询操作设置事务属性
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberMapper memberMapper;

    /**
     * 根据注册信息创建用户
     * 在调用该方法前，需要把VO转化为PO
     * @param record PO
     * @return
     */
    @Transactional(propagation = Propagation.REQUIRES_NEW,rollbackFor = Exception.class)
    @Override
    public int insertSelective(Member record) {
        return memberMapper.insertSelective(record);
    }

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
        if(Objects.isNull(members) || members.size() == 0){
            return null;
        }
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
