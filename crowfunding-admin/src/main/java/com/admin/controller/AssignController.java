package com.admin.controller;

import com.admin.entity.Role;
import com.admin.service.AdminService;
import com.admin.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author qiu
 * @create 2022-02-09 15:14
 */
@Controller
public class AssignController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private RoleService roleService;

    /**
     * 保存角色分配
     * @param adminId
     * @param pageNum
     * @param roleIdList
     * @return
     */
    @PostMapping("/assign/save")
    public String saveAdminRoleRelationship(@RequestParam("adminId")Integer adminId,
                                            @RequestParam("pageNum")Integer pageNum,
                                            @RequestParam(value = "roleIdList",required = false)List<Integer> roleIdList){
        // 删除旧的管理员角色关系
        adminService.deleteAdminRoleRelationship(adminId);
        // 建立新的管理员角色关系
        if(roleIdList != null && roleIdList.size() > 0){
            adminService.insertAdminRoleRelationship(adminId,roleIdList);
        }

        return "redirect:/assign?adminId="+adminId+"&pageNum="+pageNum;
    }

    /**
     * 分配角色页面
     * @param adminId
     * @param pageNum
     * @param modelMap
     * @return
     */
    @RequestMapping("/assign")
    public String assignRolePage(@RequestParam("adminId")Integer adminId,
                                 @RequestParam("pageNum")Integer pageNum,
                                 ModelMap modelMap){
        // 查找已分配角色和未分配角色
        List<Role> assignedRole = roleService.getAssignedRole(adminId);
        List<Role> unassignedRole = roleService.getUnassignedRole(adminId);

        // 存入模型
        modelMap.addAttribute("assignedRole",assignedRole);
        modelMap.addAttribute("unassignedRole",unassignedRole);

        return "assign-role";
    }
}
