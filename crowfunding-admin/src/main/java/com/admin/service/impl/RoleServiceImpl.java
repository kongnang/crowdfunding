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
     * 在admin_role表中出现的角色表示已经分配
     * 通过adminId在admin_role中查找，再到role表中取出该角色信息
     * @param adminId
     * @return
     */
    @Override
    public List<Role> getAssignedRole(Integer adminId) {
        return roleMapper.getAssignedRole(adminId);
    }

    @Override
    public List<Role> getUnassignedRole(Integer adminId) {
        return roleMapper.getUnassignedRole(adminId);
    }

    /**
     * 根据id进行批量删除
     * @param ids
     * @return
     */
    @Override
    public int deleteByIds(List<Integer> ids) {
        int deleteRes = roleMapper.deleteByIds(ids);
        return deleteRes;
    }

    /**
     *
     * @param role
     * @return
     */
    @Override
    public int updateByPrimaryKey(Role role) {
        int updateRes = roleMapper.updateByPrimaryKey(role);
        return updateRes;
    }

    /**
     *
     * @param role
     * @return
     */
    @Override
    public int insert(Role role) {
        int insertRes = roleMapper.insert(role);
        return insertRes;
    }

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
