package com.admin.service;

import com.admin.entity.Admin;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author qiu
 * @create 2021-12-16 21:52
 */
public interface AdminService {
    Admin selectById(Integer id);

    Admin selectByAdminAccAndPwd(String adminAccount, String adminPassword);

    PageInfo<Admin> selectByKeyWord(String keyword, Integer pageNum, Integer pageSize);

    Boolean insertAdmin(Admin admin);

    Boolean deleteAdminById(Integer id);

    Boolean updateAdminById(Admin admin);

    int deleteAdminRoleRelationship(Integer adminId);

    int insertAdminRoleRelationship(Integer adminId,List<Integer> roleIdList);

    Admin selectByAdminAcc(String adminAcc);
}
