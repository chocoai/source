package com.jeesite.modules.asset.guideApp.entity;

/**
 * @Auther: len
 * @Date: 2018/7/27 09:29
 * @Description:
 */
public class K3Discount {
    /**
     * 优惠名称
     */
    private String offerName;
    /**
     * 优惠金额
     */
    private String offerAmount;

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public String getOfferAmount() {
        return offerAmount;
    }

    public void setOfferAmount(String offerAmount) {
        this.offerAmount = offerAmount;
    }
}
