package com.admin.controller;

import com.admin.entity.Role;
import com.admin.service.RoleService;
import com.github.pagehelper.PageInfo;
import com.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author qiu
 * @create 2022-01-14 14:39
 */
@Controller
public class RoleController {
    @Autowired
    private RoleService roleService;

    /**
     * 批量删除、单条删除
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/role/delete.json")
    public ResultEntity<String> roleDelete(@RequestBody List<Integer> ids){
        roleService.deleteByIds(ids);
        return ResultEntity.successWithoutData();
    }

    /**
     * 在模态框中更改角色，使用Ajax请求
     * @param role
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/role/update.json")
    public ResultEntity<String> roleUpdate(Role role){
        roleService.updateByPrimaryKey(role);
        return ResultEntity.successWithoutData();
    }

    /**
     * 在模态框中插入角色，使用Ajax请求
     * @param role 在Ajax请求体中的role属性
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/role/save.json")
    public ResultEntity<String> roleSave(Role role){
        roleService.insert(role);

        return ResultEntity.successWithoutData();
    }

    /**
     * 填充角色信息，使用Ajax请求
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return RusultEntity封装的Ajax请求的响应
     */
    @ResponseBody
    @RequestMapping(value = "/role/info.json")
    public ResultEntity<PageInfo<Role>> roleInfo(@RequestParam(value = "keyword",defaultValue = "")String keyword,
                                       @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                                       @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize){

        PageInfo<Role> rolePageInfo = roleService.selectByKeyword(keyword, pageNum, pageSize);

        return ResultEntity.successWithData(rolePageInfo);
    }

    /**
     * 角色维护页
     * @return
     */
    @RequestMapping(value = "/role")
    public String roleMaintainPage(){
        return "role-maintain";
    }
}
