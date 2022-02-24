package com.admin.service;

import com.admin.entity.Auth;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author qiu
 * @create 2022-02-13 21:46
 */
public interface AuthService {
    List<Auth> selectAll();

    List<Integer> selectAssignedAuthIdByRoleId(Integer roleId);

    int deleteRoleAuthRelationship(Integer roleId);

    int insertRoleAuthRelationship(@Param("roleId")Integer roleId, @Param("authIdList")List<Integer> authId);

    List<Auth> selectAssignedAuthByAdminId(Integer adminId);
}
