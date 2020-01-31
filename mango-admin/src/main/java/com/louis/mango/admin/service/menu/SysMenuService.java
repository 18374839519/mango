package com.louis.mango.admin.service.menu;

import com.louis.mango.admin.model.menu.SysMenu;

import java.util.List;
import java.util.Set;

public interface SysMenuService {

    /**
     * 查找用户的菜单权限标识集合
     * @param userName
     * @return
     */
    Set<String> selectPermissionsByUserName(String userName);
}
