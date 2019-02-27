package com.jeesite.modules.fz.appreciation.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class FzAccountRecord {
    /**
     * 用于判断查询类型，0表示全部，1表示收入
     */
    private String flag;
    /**
     * 0表示收入1表示支出
     */
    private String type;

    private String userId;
    /**
     * 获赞者Id
     */
    private String praiserId;
    /**
     * 获赞者名
     */
    private String praiserName;
    /**
     * 送赞者Id
     */
    private String presenterId;
    /**
     * 送赞者名
     */
    private String presenterName;
    /**
     * 赞币数量
     */
    private Integer coinNumber;
    /**
     * 创建时间
     */
    private Date createDate;
    private Double expendNum;		// 梵赞支出数量
    private Date expendTime;		// 支出时间
    private String expendMode;		// 消费方式
    private String anonymous;       // 是否是匿名赞赏（0否1是）

    public String getAnonymous() {
        return anonymous;
    }

    public void setAnonymous(String anonymous) {
        this.anonymous = anonymous;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getExpendNum() {
        return expendNum;
    }

    public void setExpendNum(Double expendNum) {
        this.expendNum = expendNum;
    }

    public Date getExpendTime() {
        return expendTime;
    }

    public void setExpendTime(Date expendTime) {
        this.expendTime = expendTime;
    }

    public String getExpendMode() {
        return expendMode;
    }

    public void setExpendMode(String expendMode) {
        this.expendMode = expendMode;
    }

    public String getPraiserId() {
        return praiserId;
    }

    public void setPraiserId(String praiserId) {
        this.praiserId = praiserId;
    }

    public String getPraiserName() {
        return praiserName;
    }

    public void setPraiserName(String praiserName) {
        this.praiserName = praiserName;
    }

    public String getPresenterId() {
        return presenterId;
    }

    public void setPresenterId(String presenterId) {
        this.presenterId = presenterId;
    }

    public String getPresenterName() {
        return presenterName;
    }

    public void setPresenterName(String presenterName) {
        this.presenterName = presenterName;
    }

    public Integer getCoinNumber() {
        return coinNumber;
    }

    public void setCoinNumber(Integer coinNumber) {
        this.coinNumber = coinNumber;
    }

//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
