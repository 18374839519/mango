package com.louis.mango.common.utils.datas;

import java.util.List;

/**
 * 树实体
 */
public class TreeData {

    private String id;  // id

    private String label;  // 名称

    private String upId;  // 上级目录id

    private String upNm; // 上级目录名称

    private List<TreeData> children; // 子级

    private List<?> data; // 数据

    /*public TreeData(String id, String label, String upId, String upNm) {
        this.id = id;
        this.label = label;
        this.upId = upId;
        this.upNm = upNm;
    }*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getUpId() {
        return upId;
    }

    public void setUpId(String upId) {
        this.upId = upId;
    }

    public String getUpNm() {
        return upNm;
    }

    public void setUpNm(String upNm) {
        this.upNm = upNm;
    }

    public List<TreeData> getChildren() {
        return children;
    }

    public void setChildren(List<TreeData> children) {
        this.children = children;
    }

    public List<?> getData() {
        return data;
    }

    public void setData(List<?> data) {
        this.data = data;
    }
}
