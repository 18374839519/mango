package com.louis.mango.admin.dao.role;

import com.louis.mango.admin.model.role.SysRoleDept;

import java.util.List;

public interface SysRoleDeptMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysRoleDept record);

    SysRoleDept selectByPrimaryKey(Long id);

    List<SysRoleDept> selectAll();

    int updateByPrimaryKey(SysRoleDept record);
}