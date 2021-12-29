package com.admin.mapper;

import com.admin.entity.Admin;
import org.apache.ibatis.annotations.Param;

public interface AdminMapper {
    Admin selectById(Integer id);

    Admin selectByAdminAccAndPwd(@Param("login_acct")String adminAccount,@Param("user_pswd") String adminPassword);
}