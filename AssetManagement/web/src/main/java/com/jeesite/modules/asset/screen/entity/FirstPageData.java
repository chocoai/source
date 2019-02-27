package com.jeesite.modules.asset.screen.entity;

public class FirstPageData {

    /**
     * 页面位置
     */
    private String pageLocation;
    /**
     * 商品id
     */
    private String numIid;
    /**
     * 主图
     */
    private String detailUrl;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 最低价格
     */
    private Double price;
    /**
     * 商品主图
     */
    private String picUrl;

    public String getPageLocation() {
        return pageLocation;
    }

    public void setPageLocation(String pageLocation) {
        this.pageLocation = pageLocation;
    }

    public String getNumIid() {
        return numIid;
    }

    public void setNumIid(String numIid) {
        this.numIid = numIid;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }
}
