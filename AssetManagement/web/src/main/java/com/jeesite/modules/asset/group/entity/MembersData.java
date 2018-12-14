package com.jeesite.modules.asset.group.entity;

public class MembersData {
    /**
     * 团员旺旺id
     */
    private String memberCode;
    /**
     * 购买件数
     */
    private Double num;

    public String getMemberCode() {
        return memberCode;
    }

    public void setMemberCode(String memberCode) {
        this.memberCode = memberCode;
    }

    public Double getNum() {
        return num;
    }

    public void setNum(Double num) {
        this.num = num;
    }
}
