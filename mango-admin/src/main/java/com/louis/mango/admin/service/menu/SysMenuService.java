package com.louis.mango.admin.service.menu;

import com.louis.mango.admin.model.menu.SysMenu;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface SysMenuService {

    SysMenu selectByPrimaryKey(int id);

    /**
     * 查找用户的菜单权限标识集合
     * @param userName
     * @return
     */
    Set<String> selectPermissionsByUserName(String userName);

    /**
     * 查询所有菜单
     * @return
     */
    List<SysMenu> selectAll();

    /**
     * 添加
     * @param record
     * @return
     */
    boolean insert(SysMenu record);

    /**
     * 校验名称是否存在
     * @param name
     * @param parentId
     * @return
     */
    int checkMenuName(String name, int parentId);

    /**
     * 删除
     * @param idList
     * @return
     */
    boolean deleteByPrimaryKey(List<Integer> idList);

    /**
     * 根据父节点查询
     * @param parentIdList
     * @return
     */
    List<Integer> selectByParentId(List<Integer> parentIdList);

    /**
     * 根据id修改菜单/目录
     * @param record
     * @return
     */
    boolean updateById(SysMenu record);
}
