package com.admin.mapper;

import com.admin.entity.Auth;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AuthMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Auth record);

    Auth selectByPrimaryKey(Integer id);

    List<Auth> selectAll();

    int updateByPrimaryKey(Auth record);

    List<Integer> selectAssignedAuthIdByRoleId(Integer roleId);

    int deleteRoleAuthRelationship(Integer roleId);

    int insertRoleAuthRelationship(@Param("roleId")Integer roleId, @Param("authIdList")List<Integer> authId);
}