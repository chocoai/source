package com.jeesite.modules.asset.ding.entity;

public class DepartmentData {
    private Integer treeLevel;
    private Integer userCount;
    private String parentCode;
    private String departmentId;
    private Integer treeLeaf;
    private String name;
    private String parentId;
    private String parentCodes;
    private String managerUser;

    public String getManagerUser() {
        return managerUser;
    }

    public void setManagerUser(String managerUser) {
        this.managerUser = managerUser;
    }

    public String getParentCodes() {
        return parentCodes;
    }

    public void setParentCodes(String parentCodes) {
        this.parentCodes = parentCodes;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Integer getTreeLevel() {
        return treeLevel;
    }

    public void setTreeLevel(Integer treeLevel) {
        this.treeLevel = treeLevel;
    }

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public Integer getTreeLeaf() {
        return treeLeaf;
    }

    public void setTreeLeaf(Integer treeLeaf) {
        this.treeLeaf = treeLeaf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public DepartmentData getDepartmentData(){
//        return this;
//    }
}
