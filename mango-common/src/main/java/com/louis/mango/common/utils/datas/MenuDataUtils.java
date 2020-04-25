package com.louis.mango.common.utils.datas;

import java.util.ArrayList;
import java.util.List;

/**
 * 树形菜单生成工具
 * MenuData实体类需换成实际查出的数据实体类 或 将查出的数据使用MenuData进行封装
 */
public class MenuDataUtils {

    private List<MenuData> MenuDataList = new ArrayList<>();
    public MenuDataUtils(List<MenuData> MenuDataList) {
        this.MenuDataList=MenuDataList;
    }

    //建立树形结构
    public List<MenuData> builTree(){
        List<MenuData> treeMenuDatas =new  ArrayList<>();
        for(MenuData MenuDataNode : getRootNode()) {
            MenuDataNode=buildChilTree(MenuDataNode);
            treeMenuDatas.add(MenuDataNode);
        }
        return treeMenuDatas;
    }

    //递归，建立子树形结构
    private MenuData buildChilTree(MenuData pNode){
        List<MenuData> chilMenuDatas =new  ArrayList<>();
        for(MenuData MenuDataNode : MenuDataList) {
            if(MenuDataNode.getUpId().equals(pNode.getId())) {
                chilMenuDatas.add(buildChilTree(MenuDataNode));
            }
        }
        pNode.setChildren(chilMenuDatas);
        return pNode;
    }

    //获取根节点
    private List<MenuData> getRootNode() {
        List<MenuData> rootMenuDataLists =new  ArrayList<>();
        for(MenuData MenuDataNode : MenuDataList) {
            if(MenuDataNode.getUpId().equals("0")) {
                rootMenuDataLists.add(MenuDataNode);
            }
        }
        return rootMenuDataLists;
    }


    /***********************************测试******************************************/
    public static void main(String[] args) {
        /*List<MenuData>  menuList= new ArrayList<>();
        *//*插入一些数据*//*
        menuList.add(new MenuData("GN001D000","系统管理","0","/admin"));
        menuList.add(new MenuData("GN001D100","权限管理","GN001D000","admin"));
        menuList.add(new MenuData("GN001D110","密码修改","GN001D100","admin"));
        menuList.add(new MenuData("GN001D120","新加用户","GN001D100","admin"));
        menuList.add(new MenuData("GN001D200","系统监控","GN001D000","admin"));
        menuList.add(new MenuData("GN001D210","在线用户","GN001D200","admin"));
        menuList.add(new MenuData("GN002D000","订阅区","0","admin"));
        menuList.add(new MenuData("GN003D000","未知领域","0","admin"));
        *//*让我们创建树*//*
        MenuDataUtils menuTree =new MenuDataUtils(menuList);
        menuList=menuTree.builTree();
        *//*转为json看看效果*//*
        String jsonOutput= JSON.toJSONString(menuList);
        System.out.println(jsonOutput);*/
    }
}
