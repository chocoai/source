package com.jeesite.modules.asset.order.entity.k3Info;

import java.math.BigDecimal;
import java.util.List;

public class Discount {
    /**
     * 店铺
     */
    private String shop;
    /**
     * 活动日期
     */
    private String activityDate;
    /**
     * 整单立减金额
     */
    private String wholeReducedSum;
    /**
     * 物料编码.数量
     */
    public List<VirtualQuotation> materialList;
    /**
     * 门店
     */
    private String store;

    /**
     * 是否享受油补
     */
    private String isPostageDisscount;

    public String getIsPostageDisscount() {
        return isPostageDisscount;
    }

    public void setIsPostageDisscount(String isPostageDisscount) {
        this.isPostageDisscount = isPostageDisscount;
    }

    public String getStore() {
        return store;
    }

    public void setStore(String store) {
        this.store = store;
    }

    public String getShop() {
        return shop;
    }

    public void setShop(String shop) {
        this.shop = shop;
    }

    public String getActivityDate() {
        return activityDate;
    }

    public void setActivityDate(String activityDate) {
        this.activityDate = activityDate;
    }

    public String getWholeReducedSum() {
        return wholeReducedSum;
    }

    public void setWholeReducedSum(String wholeReducedSum) {
        this.wholeReducedSum = wholeReducedSum;
    }

    public List<VirtualQuotation> getMaterialList() {
        return materialList;
    }

    public void setMaterialList(List<VirtualQuotation> materialList) {
        this.materialList = materialList;
    }
}
