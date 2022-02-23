package com.admin.mapper;

import com.admin.entity.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role role);

    Role selectByPrimaryKey(Integer id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role role);

    List<Role> selectByKeyword(String keyword);

    int deleteByIds(List<Integer> ids);

    List<Role> selectAssignedRole(Integer adminId);

    List<Role> selectUnassignedRole(Integer adminId);
}