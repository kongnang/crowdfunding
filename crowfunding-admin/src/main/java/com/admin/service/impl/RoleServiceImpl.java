package com.admin.service.impl;

import com.admin.entity.Role;
import com.admin.mapper.RoleMapper;
import com.admin.service.RoleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author qiu
 * @create 2022-01-14 14:45
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;

    /**
     * 根据关键字查询分页
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return role对象封装为PageInfo类型
     */
    @Override
    public PageInfo<Role> selectByKeyword(String keyword, Integer pageNum, Integer pageSize) {
        // 开启分页插件
        PageHelper.startPage(pageNum,pageSize);
        // 根据关键字查询
        List<Role> roles = roleMapper.selectByKeyword(keyword);

        // 将结果封装到PageInfo中
        PageInfo<Role> pageInfo = new PageInfo(roles);

        return pageInfo;
    }
}