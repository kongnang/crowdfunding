package com.admin.mapper;

import com.admin.entity.Admin;
import org.springframework.security.core.userdetails.User;

public interface AdminMapper {
    Admin selectById(Integer id);
}