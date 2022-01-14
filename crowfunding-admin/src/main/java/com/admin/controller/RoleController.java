package com.admin.controller;

import com.admin.entity.Role;
import com.admin.service.RoleService;
import com.constant.CrowFundingConstant;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author qiu
 * @create 2022-01-14 14:39
 */
@Controller
public class RoleController {
    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/rolemaintain")
    public String roleInfo(@RequestParam(value = "keyword",defaultValue = "")String keyword,
                           @RequestParam(value = "pageNum",defaultValue = "1")Integer pageNum,
                           @RequestParam(value = "pageSize",defaultValue = "5")Integer pageSize,
                           ModelMap modelMap){

        PageInfo<Role> rolePageInfo = roleService.selectByKeyword(keyword, pageNum, pageSize);

        modelMap.addAttribute(CrowFundingConstant.ROLE_MAINTAIN_PAGE,rolePageInfo);

        return "admin-rolemaintain";
    }
}
