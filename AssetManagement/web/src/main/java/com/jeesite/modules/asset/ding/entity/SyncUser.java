package com.jeesite.modules.asset.ding.entity;

import java.io.Serializable;

//
/**
 * 用于给E-learning平台同步用户的实体类
 */


public class SyncUser implements Serializable {
    private String employeeCode;
    private String userName;
    private String loginName;
    private String accountStatus;
    private String organizeCode;
    private String corpCode;
    private String positionCode;

    public SyncUser() {
    }

    public SyncUser(String employeeCode, String userName, String loginName, String accountStatus, String organizeCode, String corpCode, String positionCode) {
        this.employeeCode = employeeCode;
        this.userName = userName;
        this.loginName = loginName;
        this.accountStatus = accountStatus;
        this.organizeCode = organizeCode;
        this.corpCode = corpCode;
        this.positionCode = positionCode;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getAccountStatus() {
        return accountStatus;
    }

    public void setAccountStatus(String accountStatus) {
        this.accountStatus = accountStatus;
    }

    public String getOrganizeCode() {
        return organizeCode;
    }

    public void setOrganizeCode(String organizeCode) {
        this.organizeCode = organizeCode;
    }

    public String getCorpCode() {
        return corpCode;
    }

    public void setCorpCode(String corpCode) {
        this.corpCode = corpCode;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }
}
