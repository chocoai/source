package com.jeesite.modules.asset.guideApp.entity;

public class SkuPrice {
    /**
     * 物料编码
     */
    private String materialNumber;
    /**
     * 单价
     */
    private Double price;

    public String getMaterialNumber() {
        return materialNumber;
    }

    public void setMaterialNumber(String materialNumber) {
        this.materialNumber = materialNumber;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
