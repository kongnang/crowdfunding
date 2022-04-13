package com.member.entities.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author qiu
 * @create 2022-04-13 22:30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SponsorConfirmInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 易付宝账号
    private String payAccount;
    // 法人身份证号
    private String legalIdNumber;
}
