package com.member.service;

import com.member.entities.po.Member;

/**
 * @author qiu
 * @create 2022-03-05 21:25
 */
public interface MemberService {

    int insert(Member record);

    Member selectByPrimaryKey(Integer id);

    Member selectByLoginAcct(String loginAcct);
}
