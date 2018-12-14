package com.jeesite.modules.asset.ding.entity;

import com.jeesite.common.collect.ListUtils;

import java.util.List;

public class AccessDepartMent {

    /**
     * 用户
     */
    public List<DingUser> dingUserList = ListUtils.newArrayList();
    /**
     * 用户部门中间表
     */
    public List<DingUserDepartment> dingUserDepartmentList = ListUtils.newArrayList();
    /**
     * 部门
     */
    public List<DepartmentData> departmentList = ListUtils.newArrayList();

    public List<DingUser> getDingUserList() {
        return dingUserList;
    }

    public void setDingUserList(List<DingUser> dingUserList) {
        this.dingUserList = dingUserList;
    }

    public List<DingUserDepartment> getDingUserDepartmentList() {
        return dingUserDepartmentList;
    }

    public void setDingUserDepartmentList(List<DingUserDepartment> dingUserDepartmentList) {
        this.dingUserDepartmentList = dingUserDepartmentList;
    }

    public List<DepartmentData> getDepartmentList() {
        return departmentList;
    }

    public void setDepartmentList(List<DepartmentData> departmentList) {
        this.departmentList = departmentList;
    }

}
