package com.louis.mango.admin.service.menu.impl;

import com.louis.mango.admin.dao.menu.SysMenuMapper;
import com.louis.mango.admin.model.menu.SysMenu;
import com.louis.mango.admin.service.menu.SysMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class SysMenuServiceImpl implements SysMenuService {

    @Autowired
    private SysMenuMapper sysMenuMapper;

    @Override
    public Set<String> selectPermissionsByUserName(String userName) {
        Set<String> perms = new HashSet<>();
        List<SysMenu> sysMenus = sysMenuMapper.selectPermissionsByUserName(userName);
        for(SysMenu sysMenu : sysMenus) {
            if(sysMenu.getPerms() != null && !"".equals(sysMenu.getPerms())) {
                perms.add(sysMenu.getPerms());
            }
        }
        return perms;
    }

    @Override
    public List<SysMenu> selectAll() {
        return sysMenuMapper.selectAll();
    }

    @Override
    public boolean insert(SysMenu record) {
        return sysMenuMapper.insert(record);
    }

    @Override
    public int checkMenuName(String name, int parentId) {
        return sysMenuMapper.checkMenuName(name, parentId);
    }
}
