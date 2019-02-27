package com.jeesite.modules.asset.guideApp.entity;

import com.jeesite.common.collect.ListUtils;

import java.util.List;

/**
 * 引流线下登记单
 */
public class LineDownRegister {
    /**
     * 单号
     */
    private String FBillNo;

    /**
     * 创建时间
     */
    private String FCreateDate;
    /**
     * 客户姓名(旺旺号)
     */
    private String FCustName;
    /**
     * 电话
     */
    private String FPhone;
    /**
     *
     */
    private String fid;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    List<SpeedProgress> speedProgressList = ListUtils.newArrayList();

    public List<SpeedProgress> getSpeedProgressList() {
        return speedProgressList;
    }

    public void setSpeedProgressList(List<SpeedProgress> speedProgressList) {
        this.speedProgressList = speedProgressList;
    }

    public String getFBillNo() {
        return FBillNo;
    }

    public void setFBillNo(String FBillNo) {
        this.FBillNo = FBillNo;
    }

    public String getFCreateDate() {
        return FCreateDate;
    }

    public void setFCreateDate(String FCreateDate) {
        this.FCreateDate = FCreateDate;
    }

    public String getFCustName() {
        return FCustName;
    }

    public void setFCustName(String FCustName) {
        this.FCustName = FCustName;
    }

    public String getFPhone() {
        return FPhone;
    }

    public void setFPhone(String FPhone) {
        this.FPhone = FPhone;
    }
}
