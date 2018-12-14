package com.jeesite.modules.asset.group.entity;

import java.util.Date;

public class PurchaseData {
    private String purchaseCode;		// 团购编码
    private String wangCode;		// 团长
    private String groupPhone;		// 团长手机号
    private Date createTime;		// 创建时间
    private String detailCode;
    private String purchaseCodeDetail;
    private String memberWangCode;
    private Date createTimeDetail;

    public String getPurchaseCode() {
        return purchaseCode;
    }

    public void setPurchaseCode(String purchaseCode) {
        this.purchaseCode = purchaseCode;
    }

    public String getWangCode() {
        return wangCode;
    }

    public void setWangCode(String wangCode) {
        this.wangCode = wangCode;
    }

    public String getGroupPhone() {
        return groupPhone;
    }

    public void setGroupPhone(String groupPhone) {
        this.groupPhone = groupPhone;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getDetailCode() {
        return detailCode;
    }

    public void setDetailCode(String detailCode) {
        this.detailCode = detailCode;
    }

    public String getPurchaseCodeDetail() {
        return purchaseCodeDetail;
    }

    public void setPurchaseCodeDetail(String purchaseCodeDetail) {
        this.purchaseCodeDetail = purchaseCodeDetail;
    }

    public Date getCreateTimeDetail() {
        return createTimeDetail;
    }

    public void setCreateTimeDetail(Date createTimeDetail) {
        this.createTimeDetail = createTimeDetail;
    }

    public String getMemberWangCode() {
        return memberWangCode;
    }

    public void setMemberWangCode(String memberWangCode) {
        this.memberWangCode = memberWangCode;
    }
}
