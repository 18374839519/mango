package com.louis.mango.admin.dao.role;

import com.louis.mango.admin.model.role.SysRole;

import java.util.List;

public interface SysRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysRole record);

    SysRole selectByPrimaryKey(Long id);

    List<SysRole> selectAll();

    int updateByPrimaryKey(SysRole record);
}