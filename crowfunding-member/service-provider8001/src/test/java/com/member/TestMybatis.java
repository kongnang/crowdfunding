package com.member;

import com.member.entities.po.Member;
import com.member.mapper.MemberMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author qiu
 * @create 2022-03-05 20:00
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceProviderMain8001.class)
public class TestMybatis {
    @Autowired
    private MemberMapper memberMapper;

    @Test
    public void test(){
        Member member = new Member(null, "jack", "123", " 杰克",
                "jack@qq.com", 1, 1, "杰克", "123123", 2);
        memberMapper.insert(member);
        Member member1 = memberMapper.selectByPrimaryKey(1);
        System.out.println(member1);
    }
}
