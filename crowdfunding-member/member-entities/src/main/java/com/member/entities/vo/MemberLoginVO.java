package com.member.entities.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author qiu
 * @create 2022-03-21 9:51
 *
 * 此类存放登录用户的信息，并放入session中
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberLoginVO implements Serializable {

    private Integer id;

    private String username;

    private String email;
}
