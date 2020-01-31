package com.louis.mango.admin.dao.role;

import com.louis.mango.admin.model.role.SysRoleMenu;

import java.util.List;

public interface SysRoleMenuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysRoleMenu record);

    SysRoleMenu selectByPrimaryKey(Long id);

    List<SysRoleMenu> selectAll();

    int updateByPrimaryKey(SysRoleMenu record);
}