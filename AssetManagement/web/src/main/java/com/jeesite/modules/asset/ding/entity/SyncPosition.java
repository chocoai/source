package com.jeesite.modules.asset.ding.entity;

//
/**
 * e-learing同步数据用，岗位信息实体类
 * @author thomas
 * @version 2018-11-21
 */

public class SyncPosition {
    private String positionCode;
    private String positionName;
    private String categoryCode;
    private String categoryName;
    private String corpCode;


    public SyncPosition() {
    }

    public SyncPosition(String positionCode, String positionName, String categoryCode, String categoryName, String corpCode) {
        this.positionCode = positionCode;
        this.positionName = positionName;
        this.categoryCode = categoryCode;
        this.categoryName = categoryName;
        this.corpCode = corpCode;
    }

    public String getPositionCode() {
        return positionCode;
    }

    public void setPositionCode(String positionCode) {
        this.positionCode = positionCode;
    }

    public String getPositionName() {
        return positionName;
    }

    public void setPositionName(String positionName) {
        this.positionName = positionName;
    }

    public String getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(String categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCorpCode() {
        return corpCode;
    }

    public void setCorpCode(String corpCode) {
        this.corpCode = corpCode;
    }
}
