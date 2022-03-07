package com.admin.service.impl;

import com.admin.entity.Admin;
import com.admin.mapper.AdminMapper;
import com.admin.service.AdminService;
import com.constant.CrowFundingConstant;
import com.exception.LoginAcctAlreadyInUseException;
import com.exception.LoginFailedException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.util.CrowFundingUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.security.auth.login.FailedLoginException;
import java.util.List;

/**
 * @author qiu
 * @create 2021-12-16 21:54
 */
@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    /**
     * 根据管理员账号查询
     * @param adminAcc
     * @return
     */
    @Override
    public Admin selectByAdminAcc(String adminAcc) {
        return adminMapper.selectByAdminAcc(adminAcc);
    }

    /**
     * 建立新的管理员角色关系
     * @param adminId
     * @param roleIdList
     * @return
     */
    @Override
    public int insertAdminRoleRelationship(Integer adminId, List<Integer> roleIdList) {
        return adminMapper.insertAdminRoleRelationship(adminId,roleIdList);
    }

    /**
     * 删除旧的管理员角色关系
     * @param adminId
     * @return
     */
    @Override
    public int deleteAdminRoleRelationship(Integer adminId) {
        return adminMapper.deleteAdminRoleRelationship(adminId);
    }

    /**
     * 修改管理员信息
     * @param admin
     * @return 返回影响的行数
     */
    @Override
    public Boolean updateAdminById(Admin admin) {
        Boolean res = false;

        try{
            res = adminMapper.updateAdminById(admin);
        }catch (Exception e){
            throw e;
        }finally {
            return res;
        }
    }

    /**
     * 通过id删除指定管理员
     * @param id
     * @return 返回影响的行数
     */
    @Override
    public Boolean deleteAdminById(Integer id) {
        Boolean res = false;

        try{
            res = adminMapper.deleteAdminById(id);
        }catch (Exception e){
            throw e;
        }finally {
            return res;
        }
    }

    /**
     * 插入管理员
     * @param admin
     * @return 返回影响的行数
     */
    @Override
    public Boolean insertAdmin(Admin admin) {
        // 加密管理员密码
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        String userPswd = admin.getUserPswd();
        String encrypt = bCryptPasswordEncoder.encode(userPswd);

        admin.setUserPswd(encrypt);

        Boolean res = false;
        // 保存管理员对象到数据库中，如果用户名被占用抛出异常
        try{
            res = adminMapper.insertAdmin(admin);
        }catch (Exception e){
            // 若当前异常类是DuplicateKeyException的实例对象，说明账号名重复
            if(e instanceof DuplicateKeyException){
                throw new LoginAcctAlreadyInUseException(CrowFundingConstant.MESSAGE_LOGIN_ACCOUNT_ALREADY_IN_USE);
            }
            // 为了不掩盖错误仍然抛出当前异常
            throw e;
        }finally {
            return res;
        }
    }

    /**
     * 根据关键字查询并分页，将查询结果封装为PageInfo对象
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public PageInfo<Admin> selectByKeyWord(String keyword, Integer pageNum, Integer pageSize) {
        // 1.开启分页插件
        PageHelper.startPage(pageNum,pageSize);
        // 2.根据关键字进行查询
        List<Admin> admins = adminMapper.selectByKeyWord(keyword);

        // 3.将查询结果封装到PageInfo中
        PageInfo<Admin> pageInfo = new PageInfo(admins);

        return pageInfo;
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

    @Override
    public Admin selectById(Integer id) {
        Admin admin = adminMapper.selectById(id);
        return admin;
    }

}
