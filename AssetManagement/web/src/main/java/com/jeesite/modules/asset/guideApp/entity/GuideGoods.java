package com.jeesite.modules.asset.guideApp.entity;

/**
 * @Auther: len
 * @Date: 2018/8/1 10:00
 * @Description:
 */
public class GuideGoods {

    /**
     * 商品主图
     */
    private String picUrl;
    /**
     * 商品id
     */
    private String numId;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品购买数量
     */
    private Long quantity;
    /**
     * 商品价格
     */
    private Double price;		// 单价
    /**
     * 商品规格
     */
    private String spec;		// 规格
    /**
     * skuid
     */
    private String skuId;

    /**
     * sku图片
     */
    private String skuUrl;
    /**
     * 订单号
     */
    private String documentCode;

    public String getDocumentCode() {
        return documentCode;
    }

    public void setDocumentCode(String documentCode) {
        this.documentCode = documentCode;
    }

    public String getSkuUrl() {
        return skuUrl;
    }

    public void setSkuUrl(String skuUrl) {
        this.skuUrl = skuUrl;
    }

    public String getSkuId() {
        return skuId;
    }

    public void setSkuId(String skuId) {
        this.skuId = skuId;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public String getNumId() {
        return numId;
    }

    public void setNumId(String numId) {
        this.numId = numId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Long getQuantity() {
        return quantity;
    }

    public void setQuantity(Long quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }
}
