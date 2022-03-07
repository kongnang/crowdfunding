package com.admin.controller;

import com.admin.entity.Menu;
import com.admin.service.MenuService;
import com.util.ResultEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;

/**
 * @author qiu
 * @create 2022-01-24 20:05
 */
@Controller
public class MenuController {
    @Autowired
    private MenuService menuService;

    /**
     * 删除节点
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/menu/delete.json")
    public ResultEntity<String> deleteMenu(Integer id){
        menuService.deleteByPrimaryKey(id);
        return ResultEntity.successWithoutData();
    }

    /**
     * 更新节点，不修改pid
     * @param menu
     * @return
     */
    @ResponseBody
    @RequestMapping("/menu/update.json")
    public ResultEntity<String> updateMenu(Menu menu){
        menuService.updateByPrimaryKeySelective(menu);
        return ResultEntity.successWithoutData();
    }

    /**
     * 添加子节点
     * @param menu
     * @return
     */
    @ResponseBody
    @RequestMapping("/menu/add.json")
    public ResultEntity<String> addMenu(Menu menu){
        menuService.insertMenu(menu);
        return ResultEntity.successWithoutData();
    }

    /**
     * 取出所有节点，组装成树形结构
     * @return
     */
    @ResponseBody
    @RequestMapping("/menu/info.json")
    public ResultEntity<Menu> menuTree(){
        // 查询所有节点
        List<Menu> menus = menuService.selectAll();
        // 创建根节点
        Menu root = null;
        // 创建HashMap来存储节点，构造tree
        HashMap<Integer,Menu> menuHashMap = new HashMap<>();
        // 遍历所有节点，放入HashMap中
        for(Menu menu : menus){
            Integer id = menu.getId();
            menuHashMap.put(id,menu);
        }
        // 遍历所有节点，填充其子节点
        for(Menu menu : menus){
            Integer pid = menu.getPid();
            // 判断是否是根节点
            if (pid == null){
                root = menu;
                continue;
            }
            // 找到当前节点的父节点，将当前节点存入父节点的children
            Menu father = menuHashMap.get(pid);
            father.getChildren().add(menu);
        }
        // 返回根节点，就能得到整棵树
        return ResultEntity.successWithData(root);
    }

    /**
     * 菜单维护页面
     * @return
     */
    @PreAuthorize("hasRole('超级管理员')")
    @RequestMapping("/menu")
    public String menuPage(){
        return "menu-maintain";
    }
}
