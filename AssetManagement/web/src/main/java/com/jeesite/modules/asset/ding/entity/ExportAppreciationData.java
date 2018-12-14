package com.jeesite.modules.asset.ding.entity;

import com.jeesite.common.utils.excel.annotation.ExcelField;
import com.jeesite.common.utils.excel.annotation.ExcelFields;

public class ExportAppreciationData {
    @ExcelFields({
            @ExcelField(title="姓名", attrName="name", align=ExcelField.Align.CENTER, sort=10),
            @ExcelField(title="数量", attrName="coinNum", align=ExcelField.Align.CENTER, sort=15),
            @ExcelField(title="职位", attrName="position", align = ExcelField.Align.CENTER, sort=20),
            @ExcelField(title="部门", attrName="departmentNames",align = ExcelField.Align.CENTER, sort=30),
    })
    /**
     * 用户id
     */
    private String userid;
    /**
     * 用户名
     */
    private String name;
    /**
     * 部门名
     */
    private String departmentNames;
    /**
     *职位
     */
    private String position;
    /**
     *数量
     */
    private Long coinNum;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartmentNames() {
        return departmentNames;
    }

    public void setDepartmentNames(String departmentNames) {
        this.departmentNames = departmentNames;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Long getCoinNum() {
        return coinNum;
    }

    public void setCoinNum(Long coinNum) {
        this.coinNum = coinNum;
    }
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }


}
