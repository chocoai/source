package com.jeesite.modules.asset.group.entity;

import java.util.List;

/**
 * 购买件数
 */
public class BuyerData {
    /**
     * 折扣
     */
    private String rebate;
    /**
     * 购买总件数
     */
    private Double goodsNum;
    /**
     * 团员信息
     */
    private List<MembersData> membersList;
    private boolean isAbnormal;     // 是否异常

    public boolean isAbnormal() {
        return isAbnormal;
    }

    public void setAbnormal(boolean abnormal) {
        isAbnormal = abnormal;
    }

    public String getRebate() {
        return rebate;
    }

    public void setRebate(String rebate) {
        this.rebate = rebate;
    }

    public Double getGoodsNum() {
        return goodsNum;
    }

    public void setGoodsNum(Double goodsNum) {
        this.goodsNum = goodsNum;
    }

    public List<MembersData> getMembersList() {
        return membersList;
    }

    public void setMembersList(List<MembersData> membersList) {
        this.membersList = membersList;
    }
}
