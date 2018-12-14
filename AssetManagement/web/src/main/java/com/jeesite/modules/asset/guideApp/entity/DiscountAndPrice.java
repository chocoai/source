package com.jeesite.modules.asset.guideApp.entity;

import java.util.List;

public class DiscountAndPrice {

    // 油费补贴
    private Double oilSubsidy;
    List<K3Discount> discountList;
    List<SkuPrice> skuPriceList;

    public List<K3Discount> getDiscountList() {
        return discountList;
    }

    public void setDiscountList(List<K3Discount> discountList) {
        this.discountList = discountList;
    }

    public List<SkuPrice> getSkuPriceList() {
        return skuPriceList;
    }

    public void setSkuPriceList(List<SkuPrice> skuPriceList) {
        this.skuPriceList = skuPriceList;
    }

    public Double getOilSubsidy() {
        return oilSubsidy;
    }

    public void setOilSubsidy(Double oilSubsidy) {
        this.oilSubsidy = oilSubsidy;
    }
}
