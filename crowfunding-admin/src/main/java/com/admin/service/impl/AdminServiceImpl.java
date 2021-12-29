package com.admin.service.impl;

import com.admin.entity.Admin;
import com.admin.mapper.AdminMapper;
import com.admin.service.AdminService;
import com.constant.CrowFundingConstant;
import com.exception.LoginFailedException;
import com.util.CrowFundingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.security.auth.login.FailedLoginException;

/**
 * @author qiu
 * @create 2021-12-16 21:54
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin selectById(Integer id) {
        Admin admin = adminMapper.selectById(id);
        return admin;
    }

    /**
     * 将管理员输入的密码进行md5加密后与数据库中的密文对比，若相同则返回该管理员对象
     * @param adminAccount
     * @param adminPassword
     * @return
     */
    @Override
    public Admin selectByAdminAccAndPwd(String adminAccount, String adminPassword) {
        // 将明文进行加密
        String encrypt = CrowFundingUtil.encrypt(adminPassword);
        // 将账号和密文与数据库中的管理员账号对比
        Admin admin = adminMapper.selectByAdminAccAndPwd(adminAccount, encrypt);

        if(admin == null){
            throw new LoginFailedException(CrowFundingConstant.MESSAGE_LOGIN_FAILED);
        }

        return admin;
    }
}
