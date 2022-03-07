package com.admin.controller;

import com.admin.entity.Auth;
import com.admin.entity.Role;
import com.admin.service.AdminService;
import com.admin.service.AuthService;
import com.admin.service.RoleService;
import com.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
    @Autowired
    private AuthService authService;

    /**
     * 请求体中以map的形式存取roleId,authIdList。
     * 其中roleId,authIdList都封装为数组
     * @param map
     * @return
     */
    @ResponseBody
    @RequestMapping("/assign/auth/save.json")
    public ResultEntity<String> saveRoleAuthRelationship(@RequestBody Map<String,List<Integer>> map){
        // 获取roleId
        List<Integer> roleIdList = map.get("roleId");
        Integer roleId = roleIdList.get(0);
        // 获取authIdList
        List<Integer> authIdList = map.get("authIdList");

        authService.deleteRoleAuthRelationship(roleId);
        if(authIdList != null && authIdList.size() > 0){
            authService.insertRoleAuthRelationship(roleId,authIdList);
        }
        return ResultEntity.successWithoutData();
    }

    /**
     * 根据角色id查找已分配的权限
     * @param roleId
     * @return
     */
    @ResponseBody
    @RequestMapping("/assign/auth/info/assignedAuthId.json")
    public ResultEntity<List<Integer>> getAssignedAuth(@RequestParam("roleId")Integer roleId){
        List<Integer> roleIdList = authService.selectAssignedAuthIdByRoleId(roleId);
        return ResultEntity.successWithData(roleIdList);
    }

    /**
     * Ajax请求，获取所有auth
     * @return
     */
    @ResponseBody
    @RequestMapping("/assign/auth/info.json")
    public ResultEntity<List<Auth>> getAllAuth(){
        List<Auth> auths = authService.selectAll();
        return ResultEntity.successWithData(auths);
    }

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
        List<Role> assignedRole = roleService.selectAssignedRole(adminId);
        List<Role> unassignedRole = roleService.selectUnassignedRole(adminId);

        // 存入模型
        modelMap.addAttribute("assignedRole",assignedRole);
        modelMap.addAttribute("unassignedRole",unassignedRole);

        return "assign-role";
    }
}
