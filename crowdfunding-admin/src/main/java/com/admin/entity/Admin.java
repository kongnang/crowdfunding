package com.admin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin implements Serializable {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin.id
     *
     * @mbg.generated Tue Dec 14 22:51:13 CST 2021
     */
    private Integer id;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin.login_acct
     *
     * @mbg.generated Tue Dec 14 22:51:13 CST 2021
     */
    private String loginAcct;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin.user_pswd
     *
     * @mbg.generated Tue Dec 14 22:51:13 CST 2021
     */
    private String userPswd;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin.user_name
     *
     * @mbg.generated Tue Dec 14 22:51:13 CST 2021
     */
    private String userName;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin.email
     *
     * @mbg.generated Tue Dec 14 22:51:13 CST 2021
     */
    private String email;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column admin.create_time
     *
     * @mbg.generated Tue Dec 14 22:51:13 CST 2021
     */
    private Date createTime;

}