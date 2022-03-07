package com.admin.config;

import com.admin.entity.Admin;
import com.admin.entity.Auth;
import com.admin.entity.Role;
import com.admin.service.AdminService;
import com.admin.service.AuthService;
import com.admin.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author qiu
 * @create 2022-02-23 22:00
 */
@Component
public class CrowdfundingUserDetailsService implements UserDetailsService {
    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AuthService authService;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        // 根据用户名得到Admin对象
        Admin admin = adminService.selectByAdminAcc(userName);
        // 得到adminId
        Integer adminId = admin.getId();
        // 根据adminId查询已分配的角色和权限对象
        List<Role> assignedRoles = roleService.selectAssignedRole(adminId);
        List<Auth> assignedAuths = authService.selectAssignedAuthByAdminId(adminId);
        // 创建列表存储角色和权限
        List<GrantedAuthority> authorities = new ArrayList<>();
        // 添加角色
        for(Role role : assignedRoles){
            String roleName = "ROLE_" + role.getName();
            SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(roleName);
            authorities.add(grantedAuthority);
        }
        // 添加权限
        for(Auth auth : assignedAuths){
            String authName = auth.getName();
            SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(authName);
            authorities.add(grantedAuthority);
        }
        // 封装
        return new SecurityAdmin(admin,authorities);
    }
}
