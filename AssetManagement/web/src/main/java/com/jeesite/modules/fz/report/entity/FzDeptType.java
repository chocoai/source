package com.jeesite.modules.fz.report.entity;

import java.io.Serializable;

/**
 * 梵赞部门用的赞赏部门
 * @author easter
 * @data 2018/11/15 17:45
 */
public class FzDeptType implements Serializable {
    private String dept;            //部门
    private String useType;         //使用的梵赞类型
    private int useTime;         //使用次数

    public FzDeptType() {
    }

    public FzDeptType(String dept, String useType, int useTime) {
        this.dept = dept;
        this.useType = useType;
        this.useTime = useTime;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getUseType() {
        return useType;
    }

    public void setUseType(String useType) {
        this.useType = useType;
    }

    public int getUseTime() {
        return useTime;
    }

    public void setUseTime(int useTime) {
        this.useTime = useTime;
    }
}
