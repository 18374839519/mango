package com.louis.mango.common.utils.datas;

import com.alibaba.fastjson.JSON;

import java.util.ArrayList;
import java.util.List;

/**
 * 树形菜单生成工具
 * TreeData实体类需换成实际查出的数据实体类 或 将查出的数据使用TreeData进行封装
 */
public class TreeDataUtils {

    private List<TreeData> TreeDataList = new ArrayList<>();
    public TreeDataUtils(List<TreeData> TreeDataList) {
        this.TreeDataList=TreeDataList;
    }

    //建立树形结构
    public List<TreeData> builTree(){
        List<TreeData> treeTreeDatas =new  ArrayList<>();
        for(TreeData TreeDataNode : getRootNode()) {
            TreeDataNode=buildChilTree(TreeDataNode);
            treeTreeDatas.add(TreeDataNode);
        }
        return treeTreeDatas;
    }

    //递归，建立子树形结构
    private TreeData buildChilTree(TreeData pNode){
        List<TreeData> chilTreeDatas =new  ArrayList<>();
        for(TreeData TreeDataNode : TreeDataList) {
            if(TreeDataNode.getUpId().equals(pNode.getId())) {
                chilTreeDatas.add(buildChilTree(TreeDataNode));
            }
        }
        pNode.setChildren(chilTreeDatas);
        return pNode;
    }

    //获取根节点
    private List<TreeData> getRootNode() {
        List<TreeData> rootTreeDataLists =new  ArrayList<>();
        for(TreeData TreeDataNode : TreeDataList) {
            if(TreeDataNode.getUpId().equals("0")) {
                rootTreeDataLists.add(TreeDataNode);
            }
        }
        return rootTreeDataLists;
    }


    /***********************************测试******************************************/
    public static void main(String[] args) {
        /*List<TreeData>  menuList= new ArrayList<>();
        *//*插入一些数据*//*
        menuList.add(new TreeData("GN001D000","系统管理","0","/admin"));
        menuList.add(new TreeData("GN001D100","权限管理","GN001D000","admin"));
        menuList.add(new TreeData("GN001D110","密码修改","GN001D100","admin"));
        menuList.add(new TreeData("GN001D120","新加用户","GN001D100","admin"));
        menuList.add(new TreeData("GN001D200","系统监控","GN001D000","admin"));
        menuList.add(new TreeData("GN001D210","在线用户","GN001D200","admin"));
        menuList.add(new TreeData("GN002D000","订阅区","0","admin"));
        menuList.add(new TreeData("GN003D000","未知领域","0","admin"));
        *//*让我们创建树*//*
        TreeDataUtils menuTree =new TreeDataUtils(menuList);
        menuList=menuTree.builTree();
        *//*转为json看看效果*//*
        String jsonOutput= JSON.toJSONString(menuList);
        System.out.println(jsonOutput);*/
    }
}
