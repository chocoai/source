package com.jeesite.modules.fz.report.entity;

import java.io.Serializable;

/**
 * 一级部门的各个赞赏数据
 * @author easter
 * @data 2018/11/14 18:55
 */
public class FzDeptData implements Serializable,Comparable<FzDeptData> {
    private String presenterDepartment; // 赠送者部门
    private String preDept;              //赠送部门id
    private String praiserDepartment;		// 获赠者部门
    private String praDept;               //获赠者部门id
    private int number;                   //赞赏次数

    public FzDeptData() {
    }

    public FzDeptData(String presenterDepartment, String praiserDepartment, int number) {
        this.presenterDepartment = presenterDepartment;
        this.praiserDepartment = praiserDepartment;
        this.number = number;
    }

    public String getPreDept() {
        return preDept;
    }

    public void setPreDept(String preDept) {
        this.preDept = preDept;
    }

    public String getPraDept() {
        return praDept;
    }

    public void setPraDept(String praDept) {
        this.praDept = praDept;
    }

    public String getPresenterDepartment() {
        return presenterDepartment;
    }

    public void setPresenterDepartment(String presenterDepartment) {
        this.presenterDepartment = presenterDepartment;
    }

    public String getPraiserDepartment() {
        return praiserDepartment;
    }

    public void setPraiserDepartment(String praiserDepartment) {
        this.praiserDepartment = praiserDepartment;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public int compareTo(FzDeptData o) {
        int cmp = this.number - o.getNumber();
        return cmp;
    }

}
