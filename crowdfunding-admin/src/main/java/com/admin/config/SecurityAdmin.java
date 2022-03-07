package com.admin.config;

import com.admin.entity.Admin;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.List;

/**
 * 由于User类只能封装用户的账号密码，不能封装其它信息
 * 所以使用该类进行封装，使用get方法得到admin对象，从而得到admin对象其它属性
 *
 * @author qiu
 * @create 2022-02-24 14:16
 */
public class SecurityAdmin extends User {

    private Admin originalAdmin;

    public SecurityAdmin(Admin originalAdmin, List<GrantedAuthority> authorities) {
        super(originalAdmin.getUserName(), originalAdmin.getUserPswd(), authorities);

        this.originalAdmin = originalAdmin;

        // 擦除原始对象中的密码，避免密码通过security标签的principal得到
        originalAdmin.setUserPswd(null);
    }

    public Admin getOriginalAdmin() {
        return originalAdmin;
    }
}
