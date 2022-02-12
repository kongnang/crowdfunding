package com.admin.mapper;

import com.admin.entity.Admin;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminMapper {
    Admin selectById(Integer id);

    Admin selectByAdminAccAndPwd(@Param("login_acct")String adminAccount,@Param("user_pswd") String adminPassword);

    List<Admin> selectByKeyWord(String keyword);

    Boolean insertAdmin(Admin admin);

    Boolean deleteAdminById(Integer id);

    Boolean updateAdminById(Admin admin);

    int deleteAdminRoleRelationship(Integer adminId);

    int insertAdminRoleRelationship(@Param("adminId")Integer adminId,@Param("roleIdList") List<Integer> roleIdList);
}