package com.admin.service;

import com.admin.entity.Menu;

import java.util.List;

/**
 * @author qiu
 * @create 2022-01-24 20:06
 */
public interface MenuService {
    List<Menu> selectAll();
    Integer insertMenu(Menu menu);
    Integer updateByPrimaryKeySelective(Menu menu);
    Integer deleteByPrimaryKey(Integer id);
}
