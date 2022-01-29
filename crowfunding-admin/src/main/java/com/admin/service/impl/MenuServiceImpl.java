package com.admin.service.impl;

import com.admin.mapper.MenuMapper;
import com.admin.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.admin.entity.Menu;

import java.util.List;

/**
 * @author qiu
 * @create 2022-01-24 20:06
 */
@Service
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;

    @Override
    public Integer deleteByPrimaryKey(Integer id) {
        return menuMapper.deleteByPrimaryKey(id);
    }

    /**
     * 选择性更新，当传入值为null时，不修改值
     * @param menu
     * @return
     */
    @Override
    public Integer updateByPrimaryKeySelective(Menu menu) {
        return menuMapper.updateByPrimaryKeySelective(menu);
    }

    @Override
    public Integer insertMenu(Menu menu) {
        return menuMapper.insert(menu);
    }

    @Override
    public List<Menu> selectAll() {
        return menuMapper.selectAll();
    }
}
