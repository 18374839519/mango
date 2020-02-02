package com.louis.mango.admin.dao.user;

import com.louis.mango.admin.model.user.SysUser;

import java.util.List;

public interface SysUserMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysUser record);

    SysUser selectByPrimaryKey(Long id);

    List<SysUser> selectAll(SysUser sysUser);

    int updateByPrimaryKey(SysUser record);

    SysUser selectByUserName(String userName);

    /**
     * 添加用户
     * @param sysUser
     * @return
     */
    int addUser(SysUser sysUser);

    /**
     * 检查用户名是否存在
     * @param userName
     * @return
     */
    int checkUserName(String userName);

    /**
     * 检查昵称是否存在
     * @param userName
     * @return
     */
    int checkNickName(String nickName);
}