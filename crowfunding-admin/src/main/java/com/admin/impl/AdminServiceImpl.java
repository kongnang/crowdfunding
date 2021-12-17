package com.admin.impl;

import com.admin.entity.Admin;
import com.admin.mapper.AdminMapper;
import com.admin.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author qiu
 * @create 2021-12-16 21:54
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdminMapper adminMapper;

    @Override
    public Admin selectById(Integer id) {
        Admin admin = adminMapper.selectById(id);
        return admin;
    }
}
