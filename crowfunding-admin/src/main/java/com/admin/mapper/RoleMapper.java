package com.admin.mapper;

import com.admin.entity.Role;
import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role role);

    Role selectByPrimaryKey(Integer id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role role);

    List<Role> selectByKeyword(String keyword);

    int deleteByIds(List<Integer> ids);

    List<Role> getAssignedRole(Integer adminId);

    List<Role> getUnassignedRole(Integer adminId);
}