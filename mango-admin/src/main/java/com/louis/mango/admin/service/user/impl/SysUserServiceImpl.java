package com.louis.mango.admin.service.user.impl;

import com.louis.mango.admin.dao.user.SysUserMapper;
import com.louis.mango.admin.model.user.SysUser;
import com.louis.mango.admin.security.utils.PasswordUtils;
import com.louis.mango.admin.service.user.SysUserService;
import com.louis.mango.common.utils.IntByteUtils;
import com.louis.mango.common.utils.time.TimeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public List<SysUser> selectAllByPage(SysUser sysUser) {
        return sysUserMapper.selectAll(sysUser);
    }

    @Override
    public List<SysUser> selectAll(SysUser sysUser) {
        return sysUserMapper.selectAll(sysUser);
    }

    @Override
    public SysUser selectByUserName(String userName) {
        return sysUserMapper.selectByUserName(userName);
    }

    @Override
    public int addUser(SysUser sysUser) {
        sysUser.setSalt(PasswordUtils.getSalt());  // 获取盐
        sysUser.setPassword(PasswordUtils.encode(sysUser.getPassword(), sysUser.getSalt()));  // 密码加密
        sysUser.setCreateTime(new Date());
        sysUser.setStatus(1);
        sysUser.setDelFlag(0);
        if (sysUser.getNickName() == null || "".equals(sysUser.getNickName())) {
            sysUser.setNickName("游客" + TimeUtils.getTimeMillis());
        }
        return sysUserMapper.addUser(sysUser);
    }

    @Override
    public int checkUserName(String userName) {
        return sysUserMapper.checkUserName(userName);
    }

    @Override
    public int checkNickName(String nickName) {
        return sysUserMapper.checkNickName(nickName);
    }

}
