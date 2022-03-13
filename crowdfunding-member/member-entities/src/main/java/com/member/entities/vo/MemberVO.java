package com.member.entities.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author qiu
 * @create 2022-03-09 21:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberVO {

    private String loginacct;

    private String userpswd;

    private String username;

    private String email;

    private String phonenum;

    private String code;
}
