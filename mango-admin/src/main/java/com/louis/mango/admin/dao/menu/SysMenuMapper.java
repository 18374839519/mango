package com.louis.mango.admin.dao.menu;

import com.louis.mango.admin.model.menu.SysMenu;

import java.util.List;

public interface SysMenuMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SysMenu record);

    SysMenu selectByPrimaryKey(Long id);

    List<SysMenu> selectAll();

    int updateByPrimaryKey(SysMenu record);

    /**
     * 查找用户的菜单权限标识集合
     * @param userName
     * @return
     */
    List<SysMenu> selectPermissionsByUserName(String userName);
}