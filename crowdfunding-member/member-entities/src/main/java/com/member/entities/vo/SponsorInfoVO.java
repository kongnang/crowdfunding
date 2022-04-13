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
public class SponsorInfoVO implements Serializable {

    private static final long serialVersionUID = 1L;

    // 简单介绍
    private String descriptionSimple;
    // 详细介绍
    private String descriptionDetail;
    // 联系电话
    private String phone;
    // 客服电话
    private String customerServiceServicePhone;
}
