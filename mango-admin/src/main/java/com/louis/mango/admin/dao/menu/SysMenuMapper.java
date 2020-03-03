package com.louis.mango.admin.dao.menu;

import com.louis.mango.admin.model.menu.SysMenu;

import java.util.List;

public interface SysMenuMapper {
    boolean deleteByPrimaryKey(Long id);

    boolean insert(SysMenu record);

    SysMenu selectByPrimaryKey(Long id);

    List<SysMenu> selectAll();

    boolean updateByPrimaryKey(SysMenu record);

    /**
     * 查找用户的菜单权限标识集合
     * @param userName
     * @return
     */
    List<SysMenu> selectPermissionsByUserName(String userName);

    /**
     * 校验名称是否存在
     * @param name
     * @param parentId
     * @return
     */
    int checkMenuName(String name, int parentId);
}