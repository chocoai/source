package com.jeesite.modules.asset.ding.entity;

import java.util.ArrayList;
import java.util.List;

public class MenuTreeModel {
    private String departmentId;		// 部门id
    private String name;		// 部门名称
    private String parentid;		// 父部门id，根部门为1
    private String order;		// 在父部门中的次序值
   private String treeLevel;
   private String treeLeaf;
   private List<DingDepartment> childList=new ArrayList<DingDepartment>();

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getTreeLevel() {
        return treeLevel;
    }

    public void setTreeLevel(String treeLevel) {
        this.treeLevel = treeLevel;
    }

    public String getTreeLeaf() {
        return treeLeaf;
    }

    public void setTreeLeaf(String treeLeaf) {
        this.treeLeaf = treeLeaf;
    }

    public List<DingDepartment> getChildList() {
        return childList;
    }

    public void setChildList(List<DingDepartment> childList) {
        this.childList = childList;
    }
}
