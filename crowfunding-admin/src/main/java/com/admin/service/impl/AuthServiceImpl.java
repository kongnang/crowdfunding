package com.admin.service.impl;

import com.admin.entity.Auth;
import com.admin.mapper.AuthMapper;
import com.admin.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author qiu
 * @create 2022-02-13 21:49
 */
@Service
public class AuthServiceImpl implements AuthService {
    @Autowired
    private AuthMapper authMapper;

    /**
     * 通过adminId查找已分配的auth
     * @param adminId
     * @return
     */
    @Override
    public List<Auth> selectAssignedAuthByAdminId(Integer adminId) {
        return authMapper.selectAssignedAuthByAdminId(adminId);
    }

    /**
     * 建立新的角色权限关系
     * @param roleId
     * @param authId
     * @return
     */
    @Override
    public int insertRoleAuthRelationship(Integer roleId, List<Integer> authId) {
        return authMapper.insertRoleAuthRelationship(roleId,authId);
    }

    /**
     * 删除旧的角色权限关系
     * @param roleId
     * @return
     */
    @Override
    public int deleteRoleAuthRelationship(Integer roleId) {
        return authMapper.deleteRoleAuthRelationship(roleId);
    }
    /**
     * 通过角色id查询已分配的权限
     * @param roleId
     * @return
     */
    @Override
    public List<Integer> selectAssignedAuthIdByRoleId(Integer roleId) {
        return authMapper.selectAssignedAuthIdByRoleId(roleId);
    }

    /**
     *
     * @return
     */
    @Override
    public List<Auth> selectAll() {
        return authMapper.selectAll();
    }
}
