package com.admin.service;

import com.admin.entity.Role;
import com.github.pagehelper.PageInfo;

import java.util.List;

/**
 * @author qiu
 * @create 2022-01-14 14:44
 */
public interface RoleService {
    PageInfo<Role> selectByKeyword(String keyword ,Integer pageNum, Integer pageSize);

    int insert(Role role);

    int updateByPrimaryKey(Role role);

    int deleteByIds(List<Integer> ids);
}
